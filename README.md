# SparkVisuals Android — development repository

**Target:** Minecraft Bedrock Android 1.26.50.4.

This repository is being developed toward a real in-game client. The checked-in Android APK is only a standalone UI prototype: it does not render an overlay in Minecraft and is not the finished client.

## Native module

- Native source: `SparkVisualsNative/`
- Build workflow: [Native Android mod build](.github/workflows/native-android-build.yml)
- Output package: ARM64 `.levipack` (loader module; not a standalone APK)
- Module code uses documented mod-menu drawing API for a static black/red test panel.
- HUD panel scale and X/Y offset settings are saved in `settings.cfg`.
- Settings format v2 accepts and migrates v1 files.
- The test panel shows only static labels; game state/data is not connected.
- Module has not been runtime-loaded or device-tested in Minecraft 1.26.50.4.

## Android companion prototype

Gradle project: `SparkVisualsApp/`. Workflow: [Build Android prototype](.github/workflows/android-apk.yml). It is separate from the in-game native module and is not a client.

## Research and roadmap

- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Native client roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)

## Status

- [x] Native ARM64 compile/link and package creation verified in CI (prior revisions).
- [x] Settings and waypoint core unit tests previously passed.
- [ ] Latest revision adding HUD X/Y offsets and v1-to-v2 settings migration is currently under CI validation.
- [ ] In-game loading and rendering verified on-device.
- [ ] Exact target version/device testing.
- [ ] Real harmless game-data HUD features (only if documented APIs provide data).

No process injection, game hooks/patches, protection bypass, or competitive gameplay automation will be implemented. Any in-game functionality must be demonstrated in the target game before being described as working.
