# SparkVisuals Android — development repository

**Target game:** Minecraft Bedrock Android 1.26.50.4  
**User requirement:** actual integrated client, not companion app; no Resource Packs, Behavior Packs, Creator Add-ons, separately imported native-mod packages, or code injection/hooks.

## Honest status
- `SparkVisualsApp/` is a standalone Android companion/UI prototype. It can launch Minecraft and show a draggable external preview overlay. Its FPS/coordinates are placeholders/manual values; it does not read game state or draw inside Minecraft.
- `SparkVisualsNative/` is experimental native source and testable settings/waypoint core. Source contains a cosmetic status-panel draw-command call through the documented LeviLauncher Mod Menu API, but native SDK build and in-game rendering are unverified. It is not the requested standalone client.
- LeviLauncher/Preloader documentation confirms cosmetic HUD draw commands and HUD-editor API, but that route still requires native-module loading/separate package and does not expose verified live game telemetry; it is excluded by the user's constraints.
- No SparkVisuals in-game client APK exists. No module is claimed to work in-game.

## Repository
- Android prototype: `SparkVisualsApp/`
- Experimental native source and host tests: `SparkVisualsNative/`
- Android prototype CI: `.github/workflows/android-apk.yml`
- Native core tests: `.github/workflows/native-core-tests.yml`

## Research
- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Native client roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)
- [On-device test checklist](NATIVE_TESTING.md)
- [Android feasibility research](SPARKVISUALS_ANDROID_FEASIBILITY_2026-11-19.md)
- [Research log](SPARKVISUALS_RESEARCH_LOG.md)
- [Cinnabar Android candidate assessment](CINNABAR_ANDROID_CANDIDATE.md) — standalone Android client candidate, but target-version/license/asset-policy gaps remain
- [Zuri Android portability assessment](ZURI_ANDROID_CANDIDATE.md) — MIT/Bevy client, incomplete and Android target not verified

## Scope
Standalone cosmetic UI/configuration and personal waypoint storage only. No game-state item/entity detection, projectile prediction, competitive helpers, gameplay automation, undocumented hooks, process injection, signature scanning, patches, or protection bypass.

A feature may be called functional only after it is built and demonstrated on the exact Minecraft version/device. Do not publish a prototype, companion, or native-loader scaffold as a client.
