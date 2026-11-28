//! Minimal UDP transport for the first RakNet discovery packets.
//! This is not a reliable transport and does not complete the RakNet handshake.
use std::io;
use std::net::{IpAddr, Ipv4Addr, Ipv6Addr, SocketAddr, UdpSocket};
use std::time::Duration;

use crate::raknet::{decode_unconnected_pong, encode_open_connection_request_1, encode_unconnected_ping, PacketError};

#[derive(Debug)]
pub enum TransportError {
    Io(io::Error),
    Packet(PacketError),
    UnexpectedSource { expected: SocketAddr, actual: SocketAddr },
    PingTimestampMismatch { expected: i64, actual: i64 },
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
        // Bind to the matching IP family; IPv4-only binding breaks IPv6 server addresses.
        let bind_addr = match server.ip() {
            IpAddr::V4(_) => SocketAddr::new(IpAddr::V4(Ipv4Addr::UNSPECIFIED), 0),
            IpAddr::V6(_) => SocketAddr::new(IpAddr::V6(Ipv6Addr::UNSPECIFIED), 0),
        };
        let socket = UdpSocket::bind(bind_addr)?;
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
        let pong = decode_unconnected_pong(&buf[..size])?;
        if pong.0 != timestamp {
            return Err(TransportError::PingTimestampMismatch { expected: timestamp, actual: pong.0 });
        }
        Ok(pong)
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
    fn bind_uses_server_address_family() {
        // Skip if the host has IPv6 disabled; this test does not send traffic.
        let peer = match UdpSocket::bind("[::1]:0") {
            Ok(peer) => peer,
            Err(_) => return,
        };
        let addr = peer.local_addr().unwrap();
        let transport = RakNetUdp::bind(addr, Duration::from_millis(100)).unwrap();
        assert!(transport.local_addr().unwrap().is_ipv6());
    }

    #[test]
    fn ping_sends_packet_and_decodes_mock_pong() {
        let peer = UdpSocket::bind("127.0.0.1:0").unwrap();
        peer.set_read_timeout(Some(Duration::from_secs(1))).unwrap();
        let addr = peer.local_addr().unwrap();
        let transport = RakNetUdp::bind(addr, Duration::from_secs(1)).unwrap();
        let server = thread::spawn(move || {
            let mut buf = [0u8; 64];
            let (len, source) = peer.recv_from(&mut buf).unwrap();
            assert_eq!(len, 33);
            assert_eq!(buf[0], ID_UNCONNECTED_PING);
            assert_eq!(&buf[1..9], &77i64.to_be_bytes());
            assert_eq!(&buf[9..25], &MAGIC);
            assert_eq!(&buf[25..33], &88i64.to_be_bytes());

            let mut pong = vec![0x1c];
            pong.extend_from_slice(&77i64.to_be_bytes());
            pong.extend_from_slice(&900i64.to_be_bytes());
            pong.extend_from_slice(&MAGIC);
            pong.extend_from_slice(b"mock-motd");
            peer.send_to(&pong, source).unwrap();
        });
        assert_eq!(transport.ping(77, 88).unwrap(), (77, 900, b"mock-motd".to_vec()));
        server.join().unwrap();
    }

    #[test]
    fn ping_rejects_pong_with_unmatched_timestamp() {
        let peer = UdpSocket::bind("127.0.0.1:0").unwrap();
        peer.set_read_timeout(Some(Duration::from_secs(1))).unwrap();
        let addr = peer.local_addr().unwrap();
        let transport = RakNetUdp::bind(addr, Duration::from_secs(1)).unwrap();
        let server = thread::spawn(move || {
            let mut buf = [0u8; 64];
            let (_, source) = peer.recv_from(&mut buf).unwrap();
            let mut pong = vec![0x1c];
            pong.extend_from_slice(&123i64.to_be_bytes()); // deliberately not the ping timestamp
            pong.extend_from_slice(&900i64.to_be_bytes());
            pong.extend_from_slice(&MAGIC);
            pong.extend_from_slice(b"mock-motd");
            peer.send_to(&pong, source).unwrap();
        });
        let result = transport.ping(77, 88);
        assert!(matches!(result, Err(TransportError::PingTimestampMismatch { expected: 77, actual: 123 })));
        server.join().unwrap();
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

    #[test]
    fn offline_handshake_completes_against_local_mock_peer() {
        let peer = UdpSocket::bind("127.0.0.1:0").unwrap();
        peer.set_read_timeout(Some(Duration::from_secs(1))).unwrap();
        let addr = peer.local_addr().unwrap();
        let transport = RakNetUdp::bind(addr, Duration::from_secs(1)).unwrap();

        let server = thread::spawn(move || {
            let mut buf = [0u8; 2048];
            let (len, client) = peer.recv_from(&mut buf).unwrap();
            assert_eq!(len, 600);
            assert_eq!(buf[0], 0x05);
            assert_eq!(&buf[1..17], &MAGIC);

            let server_guid = 0x1234i64;
            let mut reply1 = vec![0x06];
            reply1.extend_from_slice(&server_guid.to_be_bytes());
            reply1.extend_from_slice(&MAGIC);
            reply1.push(0); // security disabled
            reply1.extend_from_slice(&1200u16.to_be_bytes());
            peer.send_to(&reply1, client).unwrap();

            let (len, client2) = peer.recv_from(&mut buf).unwrap();
            assert_eq!(client2, client);
            assert_eq!(len, 34);
            assert_eq!(buf[0], 0x07);
            assert_eq!(&buf[1..17], &MAGIC);
            assert_eq!(buf[17], 4); // IPv4 server address
            assert_eq!(&buf[24..26], &600u16.to_be_bytes());

            let mut reply2 = vec![0x08];
            reply2.extend_from_slice(&server_guid.to_be_bytes());
            reply2.push(4);
            let client_ip = match client.ip() {
                std::net::IpAddr::V4(ip) => ip.octets(),
                std::net::IpAddr::V6(_) => panic!("mock peer expects IPv4"),
            };
            reply2.extend(client_ip.map(|octet| !octet));
            reply2.extend_from_slice(&client.port().to_be_bytes());
            reply2.extend_from_slice(&600u16.to_be_bytes());
            reply2.push(0); // security disabled
            peer.send_to(&reply2, client).unwrap();
        });

        assert_eq!(transport.connect_offline(600, 77).unwrap(), 600);
        server.join().unwrap();
    }
}
