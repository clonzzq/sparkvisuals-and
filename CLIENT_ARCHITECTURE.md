# SparkVisuals — client architecture (updated 2026-11-20)

## Goal and constraints
A real in-game Android client for Minecraft Bedrock 1.26.50.4. The companion APK is not the deliverable. The user explicitly excludes Resource Packs, Behavior Packs, Creator Add-ons, and separately imported native-mod packages.

## Routes examined
- **LeviLauncher Android / Preloader:** https://github.com/LiteLDev/LeviLauncher-Android and https://levimc.org/LeviLauncher/android/guide/developer. Version 1.5.24 added Minecraft 1.26.50 support; current release observed v1.7.1 (2026-11-16). Native mods run in the game runtime and are loaded through the launcher. This is not a standalone no-loader client. Exact 1.26.50.4 device compatibility is not verified.
- **BedrockTools:** https://github.com/QYCottage/BedrockTools. MIT license; native C++ Android ARM64 modules for LeviLauncher/Preloader; its releases use a separate native-mod package. Useful as ecosystem reference, not adopted as the requested deliverable.
- **LuaClient Mobile:** https://github.com/KauanGitpy/LuaClient-Android. Public v0.1.2 launcher fork, Apache-2.0; its README/status describes ARM64, module UI, local overlay/Zoom and compatibility safe mode. Its own status lists Minecraft 1.26.45.x on Android 15 as safe mode where additional native modules are unavailable. Exact 1.26.50.4 is not verified; no code copied.
- **Xelo-Client:** https://github.com/Xelo-Client/Xelo-Client. Android client/launcher source, GPL-3.0. Its README advertises launcher/config/theme/version management and marks the in-game mod menu as not implemented. Not target-version verified.

## API and technical limits
- Official Minecraft Creator APIs for 1.26.50 are creator-content APIs, not a standalone client SDK; using those requires packs/add-ons, excluded by the user.
- Public LeviLauncher native-mod documentation provides lifecycle/config/input/menu and cosmetic drawing, but no verified public API for reading live game state. Data-dependent features require unsupported game-internal integration, which will not be implemented here.
- Android system overlays are external to Minecraft and can be restricted by Android 12+; they do not become a connected in-game HUD and are not an acceptable substitute.

## Current status
- The Android companion APK builds; its overlay shows placeholders and is outside Minecraft.
- Native source/settings/waypoint core exist; not a verified in-game client. No game device/runtime test.
- The `.levipack` packaging workflow was removed to prevent this loader-package scaffold from being mistaken for the required client.
- No SparkVisuals in-game client APK exists.

## Safe work scope
Cosmetic UI, settings, personal marker persistence, and standalone overlay UX only. No process injection, hooks, signature scans, patches, protection bypass, game-state item/entity detection, projectile prediction, or gameplay automation.

## Release gate
Never label a shell, companion, native scaffold, or package as the client. A client milestone requires an integrated client launch, exact game version/device test, and demonstrated functionality.
