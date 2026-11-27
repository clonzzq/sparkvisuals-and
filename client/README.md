# SparkVisuals standalone client (active development)

Independent client source, separate from the companion app.

## Milestone 0.1 — core and RakNet offline handshake
- [x] Dedicated Rust workspace and core crate
- [x] Target Bedrock build 1.26.50.4; protocol family 2193 (hotfix unverified)
- [x] Lifecycle state machine
- [x] RakNet offline ping/pong codecs
- [x] Open Connection Request 1 / Reply 1 and Request 2 / Reply 2 packet codecs
- [x] UDP transport for ping and two-stage offline RakNet connection exchange
- [x] CI host tests: 18/18 passed, including IPv4/IPv6 codec checks and loopback mock UDP peers (no real server/device test)
- [ ] RakNet reliability layer and retransmission/recovery
- [ ] Bedrock login/authentication
- [ ] Packet codecs/version audit
- [ ] World state/chunk decoding
- [ ] Renderer and Android surface
- [ ] Touch controls, lifecycle integration
- [ ] APK build and real server/device test

## Current limitation
Handshake source and local mock tests are implemented. GitHub Actions run #18 passed 18/18 host tests, including IPv4/IPv6 codec checks, family-aware socket binding, and mock two-stage RakNet exchange; this is not validation against a real server. The client is not playable. Secure RakNet is explicitly rejected; reliability, login/authentication, world decoding, rendering, touch controls, Android packaging and APK are still missing. No packs, add-ons, imported mods, process modification, or injection. Exact hotfix protocol compatibility and renderer asset sourcing remain unverified.

CI result: https://github.com/clonzzq/sparkvisuals-and/actions/runs/45988794133
