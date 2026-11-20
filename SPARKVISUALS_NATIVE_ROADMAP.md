# SparkVisuals native-client roadmap

## Non-negotiable goal
An integrated Android client for Minecraft Bedrock 1.26.50.4, not a companion overlay or launcher shell. User excludes Resource Packs, Behavior Packs, Creator Add-ons and separately imported mod packages.

## Research
- [x] Existing Android companion APK and native scaffold audited; neither is an in-game client.
- [x] Native Android Bedrock launcher/mod ecosystems researched.
- [x] LeviLauncher/Preloader and BedrockTools license/version details checked.
- [x] LuaClient Mobile v0.1.2 Apache-2.0 candidate reviewed at README/status level; exact target compatibility not verified.
- [x] Xelo-Client GPL-3.0 reviewed at repository/README level.
- [ ] A verified, supported integration path satisfying all user constraints and exact target version.

## Safe implementation boundary
Continue only with benign standalone UI/configuration, persistent personal waypoint storage, and documented overlay drawing that does not access game state. No process injection, hooks, signature scans, patches, protection bypass, competitive gameplay helpers, item/entity detection, projectile prediction, or gameplay automation.

## Current implementation
- Android companion APK build has succeeded previously; this is not a client.
- Native settings and waypoint core plus host tests exist.
- A static native test-panel scaffold was compiled previously, but it was not verified in-game and is not an integrated client.
- The workflow that packaged the scaffold as a `.levipack` was removed. No package output is being treated as a deliverable.
- No Minecraft 1.26.50.4 device test has been completed.

## Next checkpoint
Research a legal supported client SDK/runtime that can be integrated into one APK without external packs/addons or separate mod import. If no such API exists, continue documenting feasibility and avoid building a deceptive launcher-only APK.

## Release gate
Do not label or publish a SparkVisuals in-game client until the client itself launches Minecraft 1.26.50.4, is tested on a device, and the claimed feature is demonstrated.
