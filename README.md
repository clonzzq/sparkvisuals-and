# SparkVisuals Android — prototype (not the client)

The Android app currently is a companion/launcher prototype, **not an integrated Minecraft client**. It has a visual dashboard, locally saved waypoint list, and settings stored in the app; those settings do not render or affect Minecraft. No fake module is represented as working in-game.

## Verified build
- GitHub Actions run #15 succeeded (debug APK build and artifact upload): https://github.com/clonzzq/sparkvisuals-and/actions/runs/44510111566
- Artifact: `SparkVisuals-debug-apk` (companion prototype only; not client)
- SHA-256: `2f860277e1c37deb1325d458346b650df06133fe1aa4315376c208648ac17d54`
- APK workflow: `.github/workflows/sparkvisuals-android.yml`

## Client research
Android native client development research and documented route:
- [Architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)

The documented ecosystem candidate is LeviLauncher Android with its Preloader SDK/native-mod system. Exact MC 1.26.50.4 device compatibility and a SparkVisuals module have not been tested. We do not use Resource Packs, Behavior Packs, Creator Add-ons, fake toggles, process injection, hooks, game patches, protection bypasses, or competitive gameplay automation.

## Target
Minecraft Bedrock Android 1.26.50.4. A SparkVisuals client APK does not exist yet. The project will only be called a client after in-game loading and at least one real harmless feature are verified on a device.
