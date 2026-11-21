# SparkVisuals native-client roadmap

## Non-negotiable goal
An integrated Android client for Minecraft Bedrock 1.26.50.4, not a companion app or launcher shell. User excludes Resource Packs, Behavior Packs, Creator Add-ons, separately imported native-mod packages, and code injection/hooks.

## Research
- [x] Existing Android companion APK and native scaffold audited.
- [x] LeviLauncher/Preloader native-mod ecosystem and BedrockTools reviewed.
- [x] LuaClient Mobile and Xelo-Client candidate reviewed; exact target compatibility not verified.
- [x] Flarial Android listing reviewed; Android source/license and exact patch/device compatibility not verified.
- [x] Official Preloader Mod Menu API confirmed to include cosmetic draw commands and HUD editor support (2026-11-21).
- [ ] Verified supported route satisfying standalone integrated APK + no packs/addons/separate native module + exact target/device.
- [ ] Exact Minecraft 1.26.50.4 device runtime verification.

## Safe implementation boundary
Only benign standalone UI/configuration and personal waypoint storage may be developed. A source-level cosmetic draw-command prototype may be audited, but native in-game loading is not a deliverable under the user's strict no-injection/no-separate-module requirement. No process injection, hooks, signature scans, patches, protection bypass, game-state item/entity detection, projectile prediction, competitive helpers, or gameplay automation.

## Current implementation
- Companion APK: external preview overlay, manually entered coordinates and local waypoint-distance display. No Minecraft connection; not the client.
- Native source: marker/settings core and host tests; source contains a cosmetic status-panel `submitDrawCommands()` call via the documented Mod Menu API. It has no live game telemetry and has not been built/tested in Minecraft. The native loader/package path is excluded from the requested deliverable.
- Local verification: the current marker/settings core test cases were reproduced and passed on host (2/2). Native SDK compilation and device runtime are not verified.
- Companion Android CI succeeds; its APK artifacts are not in-game client builds.
- No client APK exists.

## Next checkpoint
Keep searching for a supported standalone integrated client SDK/runtime that needs no pack/addon, separately imported native module, or in-process modification. If only native-mod loader routes are found, document them but do not package or present them as the requested client.

## Release gate
Do not label/publish SparkVisuals as an in-game client until the integrated client launches Minecraft 1.26.50.4 on a device and each claimed feature is demonstrated.
