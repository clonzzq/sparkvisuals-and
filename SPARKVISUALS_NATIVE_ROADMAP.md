# SparkVisuals native roadmap (truthful milestones)

## 0. Current state
- Launcher app UI: prototype, source archive, previous debug build (not game-integrated).
- Native module: lifecycle/menu entry scaffold only, not a client.
- Pack packaging workflow: removed because user prohibits packs/add-ons.
- Target: Android Bedrock 1.26.50.4.

## 1. Build and source audit
- [x] Repository tree and source archive identified.
- [x] Android APK workflow identified (builds the launcher source extracted from ZIP).
- [x] Native scaffold and its exact limitation audited.
- [x] Public Android native-client ecosystem found (LeviLauncher/Preloader, BedrockTools, Xelo).
- [x] BedrockTools MIT license verified; upstream public history documents 26.50 then 26.60 support.
- [ ] Reproducible APK workflow run against current source and artifact verified.
- [ ] Verify what app source archive is used and its checksum/version.

## 2. Legal/runtime feasibility
- [ ] Check license obligations for any launcher/loader integration.
- [ ] Determine supported custom-launcher integration path for Android Preloader.
- [ ] Confirm exact MC 1.26.50.4 runtime support with a legitimate installation.
- [ ] Document any runtime/module dependency clearly.

## 3. Proof of integration
- [ ] Custom SparkVisuals APK launches owned Minecraft version.
- [ ] Native module loads in the game runtime.
- [ ] In-game menu opens/closes and persists config.
- [ ] A minimal benign HUD feature works on-device.
- [ ] Crash/compatibility test; collect logs and APK SHA.

## 4. Features (after integration proof)
Prioritize HUD / UI / saved markers and noncompetitive visual features. Keep all module controls truthful; disabled, unsupported, and unimplemented states must be clearly distinguished. Evaluate gameplay-aiding detection/prediction modules separately.

## Release criteria
No release as “SparkVisuals client” until the exact game version is verified and features have been tested in game. No Creator packs/add-ons, no separate .levipack distribution, no placeholder switches as completed modules.
