# SparkVisuals — client plan (2026-11-19)

## User's target
A real Minecraft Bedrock Android client branded SparkVisuals, target 1.26.50.4, with an in-game module menu and working HUD. Companion/waypoint-list app is not the deliverable. No Resource Packs, Behavior Packs, Creator Add-ons, or pretend toggles.

## Android reality / feasible implementation
On Android, the game engine is a licensed Minecraft APK; a third-party client typically provides a runtime/launcher and native modules, rather than redistributing a modified Minecraft engine. The candidate implementation is a custom SparkVisuals APK based on a supported Android runtime, plus an in-game native module authored against that runtime's documented SDK. This does not make a launcher UI the client; the client milestone is native module loaded in-game and tested.

## Candidate components (research, not integrated)
- LeviLauncher Android (Apache-2.0): supports native mods and version isolation; upstream release v1.7.1 (Nov 16 2026). Use only if integration path/license obligations are verified.
- BedrockTools (QYCottage): public Android native C++20 mod and SDK/event system for LeviLauncher/Preloader. LICENSE file is MIT (verified directly); README describes many HUD modules. Release v1.5.12 (Nov 7 2026). Target-version compatibility must be verified on 1.26.50.4. Do not redistribute upstream module package as SparkVisuals; use it as reference only.
- Avoid copying any code until license/attribution and runtime dependency checks are completed.

## Feature scope (first release)
Start with noncompetitive, observable features: HUD/menu framework, coordinates, FPS/ping if available through documented API, armor/effect HUD, saved markers, crosshair/zoom UI, and settings persistence. Gameplay detection/assistance (item/entity detection, projectile predictions, trap helpers) is a separate review and may not be implemented.

## Status — honest
- Existing Android APK builds, but is only a companion/launcher shell; it is not integrated with Minecraft.
- Native source is lifecycle/menu scaffold only; no in-game rendering, telemetry, or modules. It has not been tested in the target runtime.
- The last companion-app build is successful, but it is explicitly not the client and is not being treated as the deliverable.
- No SparkVisuals in-game client APK exists yet.

## Next proof gates
1. Establish a license-compliant Android runtime base; do not call the prototype client.
2. Verify that the runtime supports an owned Minecraft 1.26.50.4 installation on ARM64.
3. Build a minimal native module through a documented public SDK; avoid unsupported process modification, anti-cheat bypass, or game-protection evasion.
4. Verify module loading and one harmless HUD element on a real device; only then build out feature UI.
5. Release APK only with exact target/device test evidence and a commit/hash.

