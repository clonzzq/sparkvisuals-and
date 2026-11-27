# SparkVisuals standalone-client roadmap

## Status
No integrated client APK exists. The current Android APK is a companion prototype only. The former native-loader mod scaffold and its build/test workflow were removed on 2026-11-25 to align the repository with the no-packs/no-addons/no-process-modification requirements.

### Standalone Rust client core — early milestone
- [x] Clean-room workspace under `client/core` (MIT declaration; not a game APK)
- [x] Lifecycle state machine and target version/protocol constants
- [x] RakNet offline ping/pong packet codecs and UDP ping exchange
- [x] Offline Open Connection Request 1/Reply 1 and Request 2/Reply 2 codec + UDP exchange
- [x] Host CI: 18/18 tests pass, including IPv4/IPv6 codec coverage, family-aware UDP bind, and loopback mock peer tests (GitHub Actions run #18)
- [ ] Reliability/retransmission, secure transport and login/authentication
- [ ] Packet registry/version audit, world/chunk state
- [ ] Renderer, asset policy, Android surface/lifecycle, touch controls
- [ ] Android APK and exact-version/device/server validation

This is protocol/transport groundwork only; it does not establish a successful real-server connection or playable client. Secure RakNet is currently rejected. [Test run #18](https://github.com/clonzzq/sparkvisuals-and/actions/runs/45988794133).

## Research gates
- [x] Confirm companion-only behavior and state limitations in README.
- [x] Remove excluded native-mod package source, manifest, packaging script, and native test workflow.
- [x] Research Acacia: full viewer architecture, MIT, protocol 2193; Android packaging unverified; resource-pack assets required.
- [x] Reconfirm Cinnabar (experimental Android, newer protocol, PolyForm NC, vanilla assets) and Zuri (incomplete, Android unverified).
- [x] Review Acacia viewer's winit/wgpu Android portability; upstream viewer itself lacks Android entrypoint/touch/packaging.
- [x] Research Limonite: real Rust Bedrock client, GPL-3.0, playable singleplayer milestone, no Android build/release, reads vanilla assets and Java models; excluded by strict assets ban and singleplayer-only.
- [x] Research bedrock-rs: Apache-2.0 protocol/network toolkit with generated protocol v2193; no renderer or Android app, not an official-client HUD API.
- [x] Re-check Falcon-MC/Kestrel search result: direct primary GitHub lookup 404; cannot verify or use.
- [x] Check Torch Android preview: real native Android Java Edition client with touch controls/ClickGUI, but Java Edition 26.1.1/26.2/1.21.11 targets and AGPL-3.0; not a Bedrock base.
- [ ] Verify exact 1.26.50.4 protocol/content compatibility (same protocol number alone is insufficient).
- [ ] Determine whether any independently licensed non-Minecraft assets and renderer approach could satisfy the literal no-resource-pack rule; do not proceed under an assumption.
- [ ] Find a clean-room Android Bedrock client that has no resource-pack dependency, or document that the required combination is not currently feasible.

## If a candidate ever passes
- Establish source/license and dependency audit.
- Confirm Android target and app lifecycle.
- Build a standalone APK (no launcher overlay or separately imported mod).
- Test exact Minecraft target/device and document every verified feature.
- Do not add gameplay-advantage/automation features or unsupported process modification.
