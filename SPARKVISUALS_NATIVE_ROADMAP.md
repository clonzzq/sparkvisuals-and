# SparkVisuals native-client roadmap

## Priority reset
The goal is an actual Android Bedrock in-game client, not companion app/launcher UI. Stop spending effort on companion-only marker lists and decorative toggles.

## Status
- [x] Android companion APK CI succeeds (not a client).
- [x] Native lifecycle stub exists (not game-integrated).
- [x] Research found Android native-mod ecosystem.
- [x] LeviLauncher Android is Apache-2.0; supports native modules (runtime support/version must be checked).
- [x] BedrockTools LICENSE is MIT; README describes native SDK/event APIs and 51 modules. Upstream release v1.5.12 is dated 2026-11-07.
- [ ] Confirm target 1.26.50.4 compatibility using official/licensed game APK and supported runtime.
- [ ] Establish custom SparkVisuals runtime packaging/license plan.
- [ ] Minimal native SDK module loads inside game.
- [ ] In-game menu and a real harmless HUD element on-device.
- [ ] Feature modules and persistent settings after runtime proof.

## Work order
1. Runtime compatibility + license audit, no copying source prematurely.
2. Minimal native-module build using documented SDK/public interfaces only.
3. On-device test and crash logs for MC 1.26.50.4.
4. In-game UI framework, black/red theme.
5. HUD features: coordinates, FPS/ping where API exposes them, armor, potion effects, CPS, crosshair, zoom, markers.
6. Review gameplay helper/detection/prediction requests separately; do not implement protection bypass or competitive automation.

## Release gate
Never label launcher/companion/native stub as a client. Do not ship packs/add-ons. A client release requires verified in-game module loading, target version, device test, and APK checksum.
