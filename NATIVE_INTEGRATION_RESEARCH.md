# Native Android Bedrock research — 2026-11-21

## Verified findings
- **LeviLauncher Android:** https://github.com/LiteLDev/LeviLauncher-Android. Latest observed release v1.7.1 (2026-11-16). v1.5.24 added Minecraft 1.26.50 family support; this is not exact 1.26.50.4/device verification.
- **Preloader native-mod SDK:** https://github.com/LiteLDev/preloader-android and https://levimc.org/LeviLauncher/android/guide/developer. Public quick-start pins SDK tag 0.2.2 and uses a native mod loaded by LeviLauncher. Its packaging example outputs a separately imported `.levipack`.
- **Cosmetic HUD API (newly confirmed from official docs):** https://levimc.org/LeviLamina/developer_guides/android/api/mod_menu/ documents `pl::modmenu::submitDrawCommands()`, font/image registration, `submitHudEditorElements()`, and `getHudSurfaceSize()`. The API supports module toggles/configuration and cosmetic draw-command overlays. Documentation was last updated 2026-11-16. This API is for a native module loaded by LeviLauncher/Preloader; it is not a standalone Bedrock client SDK and does not expose verified live game data.
- **Native Quick Start/template:** https://github.com/LiteLDev/LeviLauncher-Android/tree/main/examples/full-cpp-mod and https://github.com/QYCottage/levilauncher-android-mod-template. The examples require a native module and package/import workflow. Not adopted as the final route because the user excludes separately imported mod packages and code injection/native game modification.
- **BedrockTools:** https://github.com/QYCottage/BedrockTools. Current README describes 51 modules, public game wrappers and event API; its version-specific signatures/offsets are required to connect modules to game data. No source copied or integration attempted because that crosses the excluded game-internal hook/signature boundary.
- **Creator APIs:** official Bedrock Creator APIs remain content/pack-based and are excluded.
- **External Android overlay:** not inside Minecraft and has no automatic live game-data feed; not accepted as a client.

## Repository source audit
- `SparkVisualsNative/src/SparkVisualsMod.cpp` already contains a source-level prototype that submits a cosmetic rectangular status panel through `submitDrawCommands()`, and exposes HUD scale/position/color configuration. This is an API call in source, **not proof it builds or appears in Minecraft**.
- The repository's native CMake project builds host tests when `BUILD_TESTING` is enabled. It does not currently build the native Android SDK module in CI. Exact native SDK/Android build and on-device runtime remain unverified.
- Host C++ tests for marker/settings core were manually reproduced from the current checked-in headers/tests and passed locally: 2/2. This does not validate the native SDK integration.
- Current companion APK CI succeeds, but those APKs are companion app artifacts, not SparkVisuals in-game client APKs.

## Feasibility conclusion
A documented API now confirms that a cosmetic HUD panel/draw commands can be created in the LeviLauncher native-mod ecosystem without game-state access. However, it still requires the native module loading/package route (LeviLauncher + Preloader), which is excluded by the user's strict no-separate-mod/no-injection integrated-client requirement. It also cannot provide coordinates, armor, potion state, FPS/ping, items/entities or other live Minecraft telemetry through the documented public HUD API alone.

No implementation or build artifact was created from this discovery. Do not re-label the native scaffold or companion as the requested client. Continue researching a supported standalone integrated client SDK/runtime and exact 1.26.50.4/device compatibility.

## Sources
- LeviLauncher releases: https://github.com/LiteLDev/LeviLauncher-Android/releases
- Native quick start: https://levimc.org/LeviLauncher/android/guide/developer
- Mod Menu API: https://levimc.org/LeviLamina/developer_guides/android/api/mod_menu/
- Preloader SDK: https://github.com/LiteLDev/preloader-android
- Official example: https://github.com/LiteLDev/LeviLauncher-Android/tree/main/examples/full-cpp-mod
- Android template: https://github.com/QYCottage/levilauncher-android-mod-template
- BedrockTools: https://github.com/QYCottage/BedrockTools
