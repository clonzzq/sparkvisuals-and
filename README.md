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
- Nonfunctional placeholder feature toggles were removed.
- The panel labels itself as a test and states that game data is not connected.
- It has not been runtime-loaded or device-tested in Minecraft 1.26.50.4.

## Standalone Android client prototype

Gradle project: `SparkVisualsApp/`. The APK is an independent Android app (no third-party launcher dependency) with a Play button and an opt-in draggable system overlay HUD service. It requests Android's display-over-other-apps permission. The HUD currently shows a clearly labeled preview with FPS/coordinates placeholders; it does not read Minecraft state. Build workflow: [Build Android prototype](.github/workflows/android-apk.yml).

## Research and roadmap

- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Native client roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)
- [On-device test checklist](NATIVE_TESTING.md)

## Status

- [x] Native ARM64 build and `.levipack` package verified in CI: [run 44524336966](https://github.com/clonzzq/sparkvisuals-and/actions/runs/44524336966), source commit `7c456c9`.
- [x] Settings migration, offsets and RGB parser tests passed in CI and host compile.
- [x] Scale, position, accent color and panel background controls implemented in source.
- [x] Standalone app overlay-service prototype added: opt-in overlay permission, draggable black/red HUD preview, close control, launch game shortcut.\n- [ ] CI build for standalone overlay APK after latest changes.\n- [ ] On-device overlay verification in Minecraft.
- [ ] Exact target version/device test.
- [ ] Real harmless game-data HUD features, only if available through documented public APIs.

The Android launcher SDK exposes lifecycle, configuration, input and Mod Menu APIs but no public game-state API. Game-state-dependent features would require undocumented game internals; they are not implemented here.

No process injection, game hooks/patches, protection bypass, or competitive gameplay automation will be implemented. Features described as working in-game must be demonstrated in Minecraft first.
