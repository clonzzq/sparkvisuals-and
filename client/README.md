# SparkVisuals standalone client (active development)

Independent client source, separate from the companion app.

## Milestone 0.1 — core and UDP discovery
- [x] Dedicated Rust workspace and core crate
- [x] Target Bedrock build 1.26.50.4; protocol family 2193 (hotfix unverified)
- [x] Lifecycle state machine
- [x] RakNet offline ping/pong codecs and Open Connection Request 1 encoder
- [x] UDP socket wrapper sends discovery packets and receives/validates pong (local tests written; execution pending)
- [ ] RakNet handshake stages, retries, and reliability layer
- [ ] Bedrock login/authentication
- [ ] Packet codecs/version audit
- [ ] World state/chunk decoding
- [ ] Renderer and Android surface
- [ ] Touch controls, lifecycle integration
- [ ] APK build and real server/device test

## Current limitation
This code still does not complete a server connection, authenticate, render, or produce an APK. It is standalone client-side code, not a companion/overlay. No packs, add-ons, imported mods, process modification, or injection. Exact hotfix protocol compatibility and permissible renderer asset sourcing remain unverified.
