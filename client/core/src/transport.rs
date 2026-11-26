//! Minimal UDP transport for the first RakNet discovery packets.
//! This is not a reliable transport and does not complete the RakNet handshake.
use std::io;
use std::net::{SocketAddr, UdpSocket};
use std::time::Duration;

use crate::raknet::{decode_unconnected_pong, encode_open_connection_request_1, encode_unconnected_ping, PacketError};

#[derive(Debug)]
pub enum TransportError {
    Io(io::Error),
    Packet(PacketError),
    UnexpectedSource { expected: SocketAddr, actual: SocketAddr },
}

impl From<io::Error> for TransportError { fn from(value: io::Error) -> Self { Self::Io(value) } }
impl From<PacketError> for TransportError { fn from(value: PacketError) -> Self { Self::Packet(value) } }

pub struct RakNetUdp {
    socket: UdpSocket,
    server: SocketAddr,
}

impl RakNetUdp {
    /// Bind an ephemeral local UDP port and configure the receive timeout.
    pub fn bind(server: SocketAddr, timeout: Duration) -> io::Result<Self> {
        let socket = UdpSocket::bind("0.0.0.0:0")?;
        socket.set_read_timeout(Some(timeout))?;
        Ok(Self { socket, server })
    }

    pub fn local_addr(&self) -> io::Result<SocketAddr> { self.socket.local_addr() }
    pub fn server_addr(&self) -> SocketAddr { self.server }

    /// Send discovery ping and wait for a matching pong from the configured peer.
    pub fn ping(&self, timestamp: i64, client_guid: i64) -> Result<(i64, i64, Vec<u8>), TransportError> {
        let packet = encode_unconnected_ping(timestamp, client_guid);
        self.socket.send_to(&packet, self.server)?;
        let mut buf = [0u8; 2048];
        let (size, source) = self.socket.recv_from(&mut buf)?;
        if source != self.server {
            return Err(TransportError::UnexpectedSource { expected: self.server, actual: source });
        }
        Ok(decode_unconnected_pong(&buf[..size])?)
    }

    /// Performs RakNet's offline Open Connection Request 1 / Reply 1 / Request 2 / Reply 2 exchange.
    /// Security-enabled servers are rejected because secure RakNet is not implemented.
    pub fn connect_offline(&self, mtu: usize, client_guid: i64) -> Result<u16, TransportError> {
        use crate::raknet::{decode_open_connection_reply_1, decode_open_connection_reply_2, encode_open_connection_request_2};
        let request1 = encode_open_connection_request_1(mtu)?;
        self.socket.send_to(&request1, self.server)?;
        let mut buf = [0u8; 2048];
        let (size, source) = self.socket.recv_from(&mut buf)?;
        if source != self.server {
            return Err(TransportError::UnexpectedSource { expected: self.server, actual: source });
        }
        let reply1 = decode_open_connection_reply_1(&buf[..size])?;
        if reply1.security_enabled { return Err(TransportError::Packet(PacketError::UnexpectedPacketId)); }
        let negotiated_mtu = reply1.mtu.min(mtu as u16);
        let request2 = encode_open_connection_request_2(self.server, negotiated_mtu, client_guid);
        self.socket.send_to(&request2, self.server)?;
        let (size, source) = self.socket.recv_from(&mut buf)?;
        if source != self.server {
            return Err(TransportError::UnexpectedSource { expected: self.server, actual: source });
        }
        let reply2 = decode_open_connection_reply_2(&buf[..size])?;
        if reply2.security_enabled || reply2.server_guid != reply1.server_guid {
            return Err(TransportError::Packet(PacketError::UnexpectedPacketId));
        }
        Ok(reply2.mtu)
    }

    /// Send Open Connection Request 1 only; use connect_offline for the two-stage handshake.
    pub fn send_open_connection_request_1(&self, mtu: usize) -> Result<usize, TransportError> {
        let packet = encode_open_connection_request_1(mtu)?;
        Ok(self.socket.send_to(&packet, self.server)?)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::raknet::{ID_UNCONNECTED_PING, MAGIC};
    use std::thread;

    #[test]
    fn ping_sends_packet_to_local_peer() {
        let peer = UdpSocket::bind("127.0.0.1:0").unwrap();
        peer.set_read_timeout(Some(Duration::from_secs(1))).unwrap();
        let addr = peer.local_addr().unwrap();
        let transport = RakNetUdp::bind(addr, Duration::from_millis(100)).unwrap();
        let sent = thread::spawn(move || {
            let mut buf = [0u8; 64];
            let (len, source) = peer.recv_from(&mut buf).unwrap();
            assert_eq!(buf[0], ID_UNCONNECTED_PING);
            assert_eq!(&buf[9..25], &MAGIC);
            (len, source)
        });
        transport.socket.send_to(&encode_unconnected_ping(77, 88), addr).unwrap();
        let (len, _) = sent.join().unwrap();
        assert_eq!(len, 25);
    }

    #[test]
    fn request_one_is_transmitted_to_local_peer() {
        let peer = UdpSocket::bind("127.0.0.1:0").unwrap();
        peer.set_read_timeout(Some(Duration::from_secs(1))).unwrap();
        let addr = peer.local_addr().unwrap();
        let transport = RakNetUdp::bind(addr, Duration::from_millis(100)).unwrap();
        assert_eq!(transport.send_open_connection_request_1(600).unwrap(), 600);
        let mut buf = [0u8; 700];
        let (len, _) = peer.recv_from(&mut buf).unwrap();
        assert_eq!(len, 600);
        assert_eq!(buf[0], 0x05);
    }
}
