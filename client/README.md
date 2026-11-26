# SparkVisuals standalone client (active development)

Independent client source, separate from the companion app.

## Milestone 0.1 — core and RakNet offline handshake
- [x] Dedicated Rust workspace and core crate
- [x] Target Bedrock build 1.26.50.4; protocol family 2193 (hotfix unverified)
- [x] Lifecycle state machine
- [x] RakNet offline ping/pong codecs
- [x] Open Connection Request 1 / Reply 1 and Request 2 / Reply 2 packet codecs
- [x] UDP transport for ping and two-stage offline RakNet connection exchange (tests authored; execution pending)
- [ ] RakNet reliability layer and retransmission/recovery
- [ ] Bedrock login/authentication
- [ ] Packet codecs/version audit
- [ ] World state/chunk decoding
- [ ] Renderer and Android surface
- [ ] Touch controls, lifecycle integration
- [ ] APK build and real server/device test

## Current limitation
Handshake source is implemented, but compatibility with actual servers is not verified and the client is not yet a playable client. Secure RakNet is explicitly rejected; reliability, login, world decoding, rendering, and APK packaging are still missing. No packs, add-ons, imported mods, process modification, or injection. Exact hotfix protocol compatibility and allowed renderer asset sourcing remain unverified.
