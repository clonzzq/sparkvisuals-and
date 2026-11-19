# SparkVisuals — client architecture (updated 2026-11-19)

## Goal
A real Minecraft Bedrock Android client, target 1.26.50.4, with SparkVisuals in-game UI/HUD. Companion app is not the deliverable. User constraints: no Resource Packs, Behavior Packs, Creator Add-ons, or fake toggles.

## Verified Android platform route
- LeviLauncher Android v1.5.24 release notes explicitly added native-mod support for Minecraft 1.26.50; v1.7.1 is current observed release (2026-11-16). This supports the target family, but exact 1.26.50.4 needs a real device test.
- LeviLauncher is an open-source launcher that uses a licensed Google Play Minecraft installation. Its Android native mods are loaded by Preloader SDK (preloader-android) and use its own `pl::` API.
- Official developer guide: https://levimc.org/LeviLauncher/android/guide/developer
- Official template: https://github.com/QYCottage/levilauncher-android-mod-template
- Preloader SDK: https://github.com/LiteLDev/preloader-android
- LeviLauncher Android: https://github.com/LiteLDev/LeviLauncher-Android
- BedrockTools (QYCottage): https://github.com/QYCottage/BedrockTools; LICENSE checked directly and is MIT; release v1.5.12 observed. Reference only; do not redistribute as SparkVisuals or copy code without attribution/license review.

## Technical limitation / honest assessment
The Android mod API is a native extension API inside the game runtime. Official FAQ documents Hook, Signature and Patch capabilities and explains mods must adapt to game version changes. Implementing features that inspect/alter game state requires in-process native code and hooking/reverse engineering. We will not provide or implement process injection, game hooks/signature scans/patches, protection bypass, or competitive gameplay automation. This means the requested full cheat-style client feature set cannot be built here. Safe cosmetic HUD/UI work can continue only via supported non-hook APIs with clear limitations.

## Feature scope that is safe to pursue
Standalone UI design, config persistence, launcher integration, benign HUD drawing where supported without hooks, coordinates/markers only if exposed by supported APIs. Features requiring item/player/entity/world inspection, aim/projectile predictions, detection, gameplay automation are out of scope.

## Current status
- Android companion/launcher APK build is successful, but it is not integrated into Minecraft.
- Native source is lifecycle/menu scaffold only, not game-integrated.
- No SparkVisuals in-game client APK exists; no target-device tests.
- Candidate loader/version support research is now documented; no runtime integration proven.

## Release gate
Never label shell, companion, stub, or package as client. Do not ship creator packs. Any client milestone requires real in-game loading, exact target build/device test, demonstrated feature, and APK hash.
