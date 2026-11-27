//! RakNet offline handshake packet primitives (not a complete transport).
//! No sockets, retries, encryption, reliability layer, or server compatibility
//! claims are implemented in this module yet.

pub const MAGIC: [u8; 16] = [
    0x00, 0xff, 0xff, 0x00, 0xfe, 0xfe, 0xfe, 0xfe,
    0xfd, 0xfd, 0xfd, 0xfd, 0x12, 0x34, 0x56, 0x78,
];
pub const ID_UNCONNECTED_PING: u8 = 0x01;
pub const ID_UNCONNECTED_PONG: u8 = 0x1c;
pub const ID_OPEN_CONNECTION_REQUEST_1: u8 = 0x05;
pub const RAKNET_PROTOCOL_VERSION: u8 = 11;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PacketError { TooShort, InvalidMagic, UnexpectedPacketId, InvalidMtu }

/// Create an unconnected ping packet (timestamp + RakNet magic + client GUID).
pub fn encode_unconnected_ping(timestamp: i64, client_guid: i64) -> Vec<u8> {
    let mut out = Vec::with_capacity(33);
    out.push(ID_UNCONNECTED_PING);
    out.extend_from_slice(&timestamp.to_be_bytes());
    out.extend_from_slice(&MAGIC);
    out.extend_from_slice(&client_guid.to_be_bytes());
    out
}

/// Decode the unconnected pong payload, returning timestamp, server GUID,
/// and the trailing server advertisement bytes (MOTD payload).
pub fn decode_unconnected_pong(packet: &[u8]) -> Result<(i64, i64, Vec<u8>), PacketError> {
    if packet.len() < 1 + 8 + 8 + 16 { return Err(PacketError::TooShort); }
    if packet[0] != ID_UNCONNECTED_PONG { return Err(PacketError::UnexpectedPacketId); }
    if packet[17..33] != MAGIC { return Err(PacketError::InvalidMagic); }
    let timestamp = i64::from_be_bytes(packet[1..9].try_into().unwrap());
    let server_guid = i64::from_be_bytes(packet[9..17].try_into().unwrap());
    Ok((timestamp, server_guid, packet[33..].to_vec()))
}

/// Encode RakNet Open Connection Request 1, padded to the requested MTU.
/// The request is protocol-version-specific; a mismatching server may reject it.
pub fn encode_open_connection_request_1(mtu: usize) -> Result<Vec<u8>, PacketError> {
    if !(576..=1492).contains(&mtu) { return Err(PacketError::InvalidMtu); }
    let mut out = Vec::with_capacity(mtu);
    out.push(ID_OPEN_CONNECTION_REQUEST_1);
    out.extend_from_slice(&MAGIC);
    out.push(RAKNET_PROTOCOL_VERSION);
    out.resize(mtu, 0);
    Ok(out)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn ping_layout_is_correct() {
        let p = encode_unconnected_ping(0x0102030405060708, 99);
        assert_eq!(p[0], ID_UNCONNECTED_PING);
        assert_eq!(&p[1..9], &0x0102030405060708i64.to_be_bytes());
        assert_eq!(&p[9..25], &MAGIC);
    }
    #[test]
    fn pong_decode_roundtrip_fields() {
        let mut p = vec![ID_UNCONNECTED_PONG];
        p.extend_from_slice(&123i64.to_be_bytes());
        p.extend_from_slice(&456i64.to_be_bytes());
        p.extend_from_slice(&MAGIC);
        p.extend_from_slice(b"motd-data");
        assert_eq!(decode_unconnected_pong(&p), Ok((123, 456, b"motd-data".to_vec())));
    }
    #[test]
    fn pong_rejects_bad_magic() {
        let mut p = vec![ID_UNCONNECTED_PONG];
        p.extend_from_slice(&1i64.to_be_bytes());
        p.extend_from_slice(&2i64.to_be_bytes());
        p.extend_from_slice(&[0;16]);
        assert_eq!(decode_unconnected_pong(&p), Err(PacketError::InvalidMagic));
    }
    #[test]
    fn request_one_has_requested_mtu_size() {
        let p = encode_open_connection_request_1(1200).unwrap();
        assert_eq!(p.len(), 1200);
        assert_eq!(p[0], ID_OPEN_CONNECTION_REQUEST_1);
        assert_eq!(&p[1..17], &MAGIC);
        assert_eq!(p[17], RAKNET_PROTOCOL_VERSION);
    }
    #[test]
    fn mtu_limits_checked() {
        assert_eq!(encode_open_connection_request_1(100), Err(PacketError::InvalidMtu));
        assert_eq!(encode_open_connection_request_1(1500), Err(PacketError::InvalidMtu));
    }
}


/// RakNet Open Connection Reply 1 decoded fields.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct OpenConnectionReply1 {
    pub server_guid: i64,
    pub mtu: u16,
    pub security_enabled: bool,
}

/// Decode Reply 1 (ID 0x06) and validate its magic.
pub fn decode_open_connection_reply_1(packet: &[u8]) -> Result<OpenConnectionReply1, PacketError> {
    if packet.len() < 1 + 8 + 16 + 1 + 2 { return Err(PacketError::TooShort); }
    if packet[0] != 0x06 { return Err(PacketError::UnexpectedPacketId); }
    if packet[9..25] != MAGIC { return Err(PacketError::InvalidMagic); }
    Ok(OpenConnectionReply1 {
        server_guid: i64::from_be_bytes(packet[1..9].try_into().unwrap()),
        security_enabled: packet[25] != 0,
        mtu: u16::from_be_bytes(packet[26..28].try_into().unwrap()),
    })
}

/// Encode Request 2 (ID 0x07) for a server endpoint and client GUID.
pub fn encode_open_connection_request_2(server: std::net::SocketAddr, mtu: u16, client_guid: i64) -> Vec<u8> {
    let mut out = Vec::with_capacity(1 + 16 + 19 + 2 + 8);
    out.push(0x07);
    out.extend_from_slice(&MAGIC);
    match server {
        std::net::SocketAddr::V4(addr) => {
            out.push(4);
            for octet in addr.ip().octets() { out.push(!octet); }
            out.extend_from_slice(&addr.port().to_be_bytes());
        }
        std::net::SocketAddr::V6(addr) => {
            out.push(6);
            out.extend_from_slice(&addr.ip().octets());
            out.extend_from_slice(&addr.port().to_be_bytes());
            out.extend_from_slice(&addr.flowinfo().to_be_bytes());
            out.extend_from_slice(&addr.scope_id().to_be_bytes());
        }
    }
    out.extend_from_slice(&mtu.to_be_bytes());
    out.extend_from_slice(&client_guid.to_be_bytes());
    out
}

#[cfg(test)]
mod handshake_packet_tests {
    use super::*;
    #[test]
    fn reply1_decodes_and_checks_magic() {
        let mut p = vec![0x06];
        p.extend_from_slice(&(-10i64).to_be_bytes());
        p.extend_from_slice(&MAGIC);
        p.push(0);
        p.extend_from_slice(&1200u16.to_be_bytes());
        assert_eq!(decode_open_connection_reply_1(&p), Ok(OpenConnectionReply1 { server_guid:-10, mtu:1200, security_enabled:false }));
        p[10] ^= 1;
        assert_eq!(decode_open_connection_reply_1(&p), Err(PacketError::InvalidMagic));
    }
    #[test]
    fn request2_ipv4_encodes_endpoint_and_guid() {
        let addr: std::net::SocketAddr = "127.0.0.1:19132".parse().unwrap();
        let p = encode_open_connection_request_2(addr, 1200, 42);
        assert_eq!(p[0], 0x07);
        assert_eq!(&p[1..17], &MAGIC);
        assert_eq!(p[17], 4);
        assert_eq!(&p[18..22], &[!127, !0, !0, !1]);
        assert_eq!(&p[22..24], &19132u16.to_be_bytes());
        assert_eq!(&p[24..26], &1200u16.to_be_bytes());
        assert_eq!(&p[26..34], &42i64.to_be_bytes());
    }
}


#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct OpenConnectionReply2 { pub server_guid: i64, pub mtu: u16, pub security_enabled: bool }

/// Decode Reply 2 packet, skipping the encoded client address field.
pub fn decode_open_connection_reply_2(packet: &[u8]) -> Result<OpenConnectionReply2, PacketError> {
    if packet.len() < 1 + 8 + 1 { return Err(PacketError::TooShort); }
    if packet[0] != 0x08 { return Err(PacketError::UnexpectedPacketId); }
    let guid = i64::from_be_bytes(packet[1..9].try_into().unwrap());
    let addr_len = match packet[9] { 4 => 7, 6 => 27, _ => return Err(PacketError::TooShort) };
    let mtu_offset = 9 + addr_len;
    if packet.len() < mtu_offset + 3 { return Err(PacketError::TooShort); }
    Ok(OpenConnectionReply2 {
        server_guid: guid,
        mtu: u16::from_be_bytes(packet[mtu_offset..mtu_offset+2].try_into().unwrap()),
        security_enabled: packet[mtu_offset+2] != 0,
    })
}

#[cfg(test)]
mod reply2_tests {
    use super::*;
    #[test]
    fn reply2_parses_ipv4_layout() {
        let mut p=vec![8];
        p.extend_from_slice(&99i64.to_be_bytes());
        p.push(4); p.extend_from_slice(&[!127,!0,!0,!1]); p.extend_from_slice(&19132u16.to_be_bytes());
        p.extend_from_slice(&1200u16.to_be_bytes()); p.push(0);
        assert_eq!(decode_open_connection_reply_2(&p), Ok(OpenConnectionReply2 {server_guid:99,mtu:1200,security_enabled:false}));
    }
}
