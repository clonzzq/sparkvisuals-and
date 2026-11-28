# SparkVisuals standalone client (active development)

Independent client source, separate from the companion app.

## Milestone 0.1 — core and RakNet offline handshake
- [x] Dedicated Rust workspace and core crate
- [x] Target Bedrock build 1.26.50.4; protocol family 2193 (hotfix unverified)
- [x] Lifecycle state machine
- [x] RakNet offline ping/pong codecs
- [x] Open Connection Request 1 / Reply 1 and Request 2 / Reply 2 packet codecs
- [x] UDP transport for ping and two-stage offline RakNet connection exchange
- [x] CI host tests: 19/19 passed, including IPv4/IPv6 codecs, loopback mock UDP peers, and rejection of a pong with an unmatched timestamp (no real server/device test)
- [ ] RakNet reliability layer and retransmission/recovery
- [ ] Bedrock login/authentication
- [ ] Packet codecs/version audit
- [ ] World state/chunk decoding
- [ ] Renderer and Android surface
- [ ] Touch controls, lifecycle integration
- [ ] APK build and real server/device test

## Current limitation
Handshake source and local mock tests are implemented. GitHub Actions run #20 passed 19/19 host tests, including IPv4/IPv6 codec checks, family-aware socket binding, mock two-stage RakNet exchange, and mismatched-pong timestamp rejection; this is not validation against a real server. The client is not playable. Secure RakNet is explicitly rejected; reliability, login/authentication, world decoding, rendering, touch controls, Android packaging and APK are still missing. No packs, add-ons, imported mods, process modification, or injection. Exact hotfix protocol compatibility and renderer asset sourcing remain unverified.

CI result: https://github.com/clonzzq/sparkvisuals-and/actions/runs/46138837835

Ping replies are accepted only when the echoed timestamp matches the request. This is mock-tested, not real-server validation.

New protocol tooling research: [Protocol 2193 report](../PROTOCOL_2193_TOOLING_RESEARCH_2026-11-28.md).
