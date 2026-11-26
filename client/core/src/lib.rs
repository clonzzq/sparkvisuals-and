//! SparkVisuals standalone Bedrock client core.
//! Networking is at an early RakNet UDP discovery milestone; world decoding,
//! rendering, authentication, and touch input are not implemented yet.

pub mod raknet;
pub mod transport;

/// Target game build for this project.
pub const TARGET_GAME_BUILD: &str = "1.26.50.4";
/// Protocol number for the 1.26.50 release family; hotfix compatibility unverified.
pub const TARGET_PROTOCOL: u32 = 2193;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ClientState { Idle, Connecting, Connected, Disconnected }
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ClientEvent { BeginConnect, ConnectionEstablished, ConnectionFailed, Disconnect }
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum TransitionError { InvalidTransition { from: ClientState, event: ClientEvent } }
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ClientLifecycle { state: ClientState }

impl Default for ClientLifecycle { fn default() -> Self { Self::new() } }
impl ClientLifecycle {
    pub const fn new() -> Self { Self { state: ClientState::Idle } }
    pub const fn state(&self) -> ClientState { self.state }
    pub fn apply(&mut self, event: ClientEvent) -> Result<ClientState, TransitionError> {
        let next = match (self.state, event) {
            (ClientState::Idle, ClientEvent::BeginConnect)
            | (ClientState::Disconnected, ClientEvent::BeginConnect) => ClientState::Connecting,
            (ClientState::Connecting, ClientEvent::ConnectionEstablished) => ClientState::Connected,
            (ClientState::Connecting, ClientEvent::ConnectionFailed)
            | (ClientState::Connecting, ClientEvent::Disconnect)
            | (ClientState::Connected, ClientEvent::Disconnect) => ClientState::Disconnected,
            _ => return Err(TransitionError::InvalidTransition { from: self.state, event }),
        };
        self.state = next;
        Ok(next)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test] fn target_identity_is_explicit() {
        assert_eq!(TARGET_GAME_BUILD, "1.26.50.4"); assert_eq!(TARGET_PROTOCOL, 2193);
    }
    #[test] fn lifecycle_transitions_and_reconnect() {
        let mut c = ClientLifecycle::new();
        assert_eq!(c.apply(ClientEvent::BeginConnect), Ok(ClientState::Connecting));
        assert_eq!(c.apply(ClientEvent::ConnectionEstablished), Ok(ClientState::Connected));
        assert_eq!(c.apply(ClientEvent::Disconnect), Ok(ClientState::Disconnected));
        assert_eq!(c.apply(ClientEvent::BeginConnect), Ok(ClientState::Connecting));
    }
    #[test] fn invalid_success_event_is_rejected() {
        let mut c = ClientLifecycle::new();
        assert!(matches!(c.apply(ClientEvent::ConnectionEstablished), Err(TransitionError::InvalidTransition { .. })));
        assert_eq!(c.state(), ClientState::Idle);
    }
    #[test] fn failed_connect_can_retry() {
        let mut c = ClientLifecycle::new();
        c.apply(ClientEvent::BeginConnect).unwrap(); c.apply(ClientEvent::ConnectionFailed).unwrap();
        c.apply(ClientEvent::BeginConnect).unwrap(); assert_eq!(c.state(), ClientState::Connecting);
    }
}
