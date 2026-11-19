# SparkVisuals Android — development repository

**Target:** Minecraft Bedrock Android 1.26.50.4.

This repository is being developed toward a real in-game client. The checked-in Android APK is only a standalone UI prototype: it does not render an overlay in Minecraft and is not the finished client.

## Native module

- Native source: `SparkVisualsNative/`
- Build workflow: [Native Android mod build](.github/workflows/native-android-build.yml)
- Output package: ARM64 `.levipack` (loader module; not a standalone APK)
- Module uses the documented Mod Menu draw API for a static black/red HUD test panel.
- Panel scale and X/Y offsets persist to `settings.cfg`; settings v2 migrates v1 files.
- Accent and background colors are configurable via Mod Menu Color entries and persisted.
- The panel labels itself as a test and states that game data is not connected.
- It has not been runtime-loaded or device-tested in Minecraft 1.26.50.4.

## Android companion prototype

Gradle project: `SparkVisualsApp/`. Workflow: [Build Android prototype](.github/workflows/android-apk.yml). This is separate from the native module and is not an in-game client.

## Research and roadmap

- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Native client roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)

## Status

- [x] Prior ARM64 native compile/link and package creation verified in CI.
- [x] Settings migration, offset and color parser unit tests pass in local host compile and core CI (latest color additions queued/running).
- [x] Scale, position, accent color and panel background controls implemented in source.
- [ ] Latest native CI package for color/position refinements.
- [ ] In-game load/render verification on device.
- [ ] Exact target version/device test.
- [ ] Real harmless game-data HUD features, only if available through documented public APIs.

The Android launcher SDK exposes lifecycle, configuration, input, and Mod Menu APIs but no public game-state API. Game-state-dependent features would require undocumented game internals; they are not implemented here.

No process injection, game hooks/patches, protection bypass, or competitive gameplay automation will be implemented. Features described as working in-game must be demonstrated in Minecraft first.
