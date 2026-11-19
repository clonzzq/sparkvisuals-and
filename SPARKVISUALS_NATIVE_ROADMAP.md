# SparkVisuals native-client roadmap

## Priority
Build toward an actual Android Bedrock in-game client, not a companion UI or launcher shell.

## Research completed
- [x] Existing Android APK and native stub audited; neither is an in-game client.
- [x] Native Android client ecosystem researched.
- [x] LeviLauncher native-mod support researched.
- [x] Official Android native mod guide, template, and Preloader SDK identified.
- [x] BedrockTools MIT license verified.
- [ ] Exact Minecraft 1.26.50.4 runtime/device test.

## Technical boundary
Continue only with benign HUD drawing and configuration through documented public APIs. No process injection, hooks, signature scans, patches, protection bypass, or competitive gameplay automation.

## Current implementation (2026-11-19)
- [x] Native ARM64 CI compile/link and `.levipack` packaging verified in earlier build.
- [x] Settings and waypoint storage with core unit tests (earlier passing revision).
- [x] Static black/red HUD test panel submitted using documented draw API; labels explicitly say game data is not connected.
- [x] HUD scale persisted in settings.
- [x] HUD X/Y offsets persisted; settings file v2 supports loading v1 settings for migration.
- [ ] CI validation of latest X/Y offset + migration commits is running.
- [ ] In-game load/render validation; source-level API use is not runtime proof.

## Next steps
- [ ] Verify current CI results and package the newest successful revision.
- [ ] Test module installation/loading with a supported launcher on device and target MC version.
- [ ] If documented public APIs expose harmless player/game state, implement one real data-backed HUD element; otherwise report API limitations.
- [ ] Expand functional HUD only after runtime proof.

## Out of scope
Entity/item detection, projectile prediction, trap/scrap/sugar detection, gameplay automation, aim or other competitive assistance.

## Release gate
No release as a SparkVisuals in-game client until load and at least one harmless data-backed HUD feature are verified in the exact target Minecraft version/device.
