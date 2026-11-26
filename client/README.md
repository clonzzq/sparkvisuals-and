# SparkVisuals standalone client (active development)

This is the standalone-client codebase, separate from the Android companion.

## Milestone 0.1 — core
- [x] Dedicated Rust workspace and core crate
- [x] Target identity: Bedrock Android 1.26.50.4
- [x] Protocol family constant 2193 (hotfix compatibility not claimed)
- [x] Typed lifecycle state machine and unit tests
- [ ] RakNet transport and handshake
- [ ] Bedrock login/authentication
- [ ] Packet codecs and version audit
- [ ] World state/chunk decoding
- [ ] Renderer and Android surface
- [ ] Touch controls, UI, lifecycle integration
- [ ] APK build and device/server test

## Limitations
The core does not connect, render, or create an APK yet. This is client-side source code, not a companion/overlay. No packs, add-ons, imported mods, process modification, or injection are included. Exact 1.26.50.4 protocol compatibility remains unverified. Renderer asset sourcing must be resolved within the project's restrictions.
