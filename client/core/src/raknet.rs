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
    let mut out = Vec::with_capacity(25);
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
