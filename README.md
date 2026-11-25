# SparkVisuals Android — development repository

**Target:** Minecraft Bedrock Android 1.26.50.4  
**Hard constraints:** no Resource Packs, Behavior Packs, Creator Add-ons, separately imported native-mod packages, process injection/hooks, patches, or protection bypass.

## Honest status
- `SparkVisualsApp/` is a companion prototype. It launches the official Minecraft app, stores personal waypoints locally, and offers an external Android overlay with manually entered coordinates and nearest-waypoint distance.
- HUD/module switches are saved preferences only; they do not render inside Minecraft or read game state.
- **No integrated SparkVisuals client APK exists.** The previous in-process native-mod scaffold and its package/test workflow were removed on 2026-11-25 because they violated the project constraints and were not a standalone client.
- No feature is represented as working in-game without exact-version/device proof.

## Repository
- Companion prototype: `SparkVisualsApp/`
- Android prototype build: `.github/workflows/android-apk.yml` (single verified workflow)
- No native-mod package or native-loader integration workflow remains.

## Research
- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Excluded in-process integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Standalone-client roadmap](SPARKVISUALS_CLIENT_ROADMAP.md)
- [Companion prototype test checklist](ANDROID_PROTOTYPE_TESTING.md)
- [Android feasibility research](SPARKVISUALS_ANDROID_FEASIBILITY_2026-11-19.md)
- [Research log](SPARKVISUALS_RESEARCH_LOG.md)
- [Cinnabar assessment](CINNABAR_ANDROID_CANDIDATE.md)
- [Zuri assessment](ZURI_ANDROID_CANDIDATE.md)
- [Acacia viewer Android assessment](ACACIA_VIEWER_ANDROID_CANDIDATE.md) — new MIT, protocol 2193 full viewer candidate; no Android build evidence, vanilla asset packs required

## Safe scope
Only companion UI/configuration, local personal waypoint storage, backups and harmless standalone UI. No live game telemetry, item/entity detection, projectile prediction, gameplay automation, or client integration via unsupported process modification.

A prototype/companion/external overlay is never labelled as the integrated client.
