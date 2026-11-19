# SparkVisuals — client architecture (updated 2026-11-19)

## Goal
Standalone branded Android APK / launcher for licensed Minecraft Bedrock Android, target 1.26.50.4, with SparkVisuals in-game UI and requested HUD. User constraint: no Resource Packs, Behavior Packs, Creator Add-ons, no fake toggles. Do not ship a separate pack as the proposed final solution.

## Verified candidate platform
- LeviLauncher Android is open-source and has Android native-mod/Preloader support. Its v1.5.24 release added inbuilt native mod support for Minecraft 1.26.50. The current v1.7.1 release is newer.
- BedrockTools is an MIT-licensed Android native C++20 mod project, with public SDK/event APIs and many HUD modules. Its upstream history shows 26.50 support and subsequent 26.60 support; target-version compatibility still must be tested on the exact 1.26.50.4 package.
- Xelo-Client is GPL-3.0 open-source Android launcher source with a launcher-based module system and integrated launcher, but its README marks in-game mod menu as unfinished.
- These are reference/build candidates only. No upstream source copied into SparkVisuals; no license review or engine integration completed.

## Candidate route (not yet built)
Investigate whether a custom SparkVisuals launcher can legally and technically bundle an Android game-launch/runtime and native-mod loader into its own APK, and then ship a separately authored/relicensed SparkVisuals native module. This is still a launcher/runtime architecture, not a modified or redistributed Minecraft engine. It may still depend on a loader runtime and native modules; if user rejects those dependencies entirely, this route does not satisfy the requirement.

## Milestones
1. **Repository/build audit:** identify APK source archive, workflow, dependencies, version/SDK configuration. Reproducibly build launcher APK and label it launcher-only.
2. **Runtime/license audit:** read upstream launcher and Preloader integration docs, check license obligations and Android target 1.26.50.4 compatibility; do not copy code without attribution and license compliance.
3. **Standalone packaging experiment:** prove a custom branded APK can launch an owned/official game installation using a supported route; no engine binaries redistributed, no packs/add-ons, no falsely represented modules.
4. **Native integration proof:** only if supported and legally acceptable; produce minimal build, verify ARM64 binary and logs, test on-device with MC 1.26.50.4. Do not claim game integration without runtime proof.
5. **Feature development:** prioritize cosmetic/noncompetitive HUD (coordinates, FPS/ping when exposed, armor/effects HUD, markers, crosshair/zoom UI) and persistence; separately assess requests that require world/game state or gameplay assistance.
6. **Release gate:** exact target version, real device/runtime launch, module rendering and interaction tests, no placeholder toggles; APK/artifact hash and source commit recorded.

## Status
- Android launcher shell exists in a ZIP source archive and a debug APK was previously produced, but it is not an integrated client.
- Native prototype is a lifecycle/menu stub only; no in-game HUD render, no game telemetry, no functional feature modules. It has not been runtime-tested.
- Native build/package workflow that produced .levipack was removed because pack/add-on delivery violates the user's constraints.
- No SparkVisuals client APK with integrated native runtime exists yet.

## Boundaries and honesty
Do not implement anti-cheat evasion, game-protection bypass, hidden-player/object detection, competitive gameplay automation, or unsupported process modification. Do not claim a toggle works unless it changes a tested in-game feature. Keep the licensed game as an externally owned dependency.
