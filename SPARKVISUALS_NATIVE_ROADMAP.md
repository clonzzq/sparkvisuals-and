# SparkVisuals native-client roadmap

## Priority
Build toward an actual Android Bedrock in-game client, not a companion UI or launcher shell.

## Research completed
- [x] Existing Android APK and native stub audited; neither is an in-game client.
- [x] Native Android client ecosystem researched.
- [x] LeviLauncher native-mod support researched.
- [x] Official Android native mod guide, template, and Preloader SDK identified.
- [x] BedrockTools MIT license verified.
- [ ] Exact Minecraft 1.26.50.4 runtime/device test.

## Technical boundary
Continue only with benign HUD drawing and configuration through documented public APIs. No process injection, hooks, signature scans, patches, protection bypass, or competitive gameplay automation. Public Preloader API exposes lifecycle, config, input and Mod Menu; it does not expose a public game-state API. That makes data-dependent features unverified/unavailable without relying on game internals.

## Current implementation (2026-11-19)
- [x] Prior native ARM64 CI compile/link and `.levipack` packaging verified.
- [x] Settings and waypoint storage with core tests.
- [x] Static black/red HUD panel using documented draw API; panel explicitly indicates game data is not connected.
- [x] Scale and X/Y panel position settings persist.
- [x] Accent and background color controls persist; parser handles #RRGGBB, 0xRRGGBB and 8-digit ARGB strings.
- [x] Settings v2 migrates older v1 settings.
- [x] Host-compiled settings tests pass locally.
- [ ] New native CI build/package for recent position and color changes is pending.
- [ ] In-game load/render validation; source/API use is not runtime proof.

## Next steps
- [ ] Finish CI and package latest successful revision.
- [ ] Import and test module in LeviLauncher on a real device, isolated game instance.
- [ ] Confirm actual HUD position, scale, and colors.
- [ ] Implement additional benign data-backed HUD features only if the supported public API exposes relevant data.
- [ ] Document limitations and tested exact game/launcher versions.

## Out of scope
Item/entity detection, projectile prediction/aim assistance, trap/scrap/sugar detection, gameplay automation, aim or other competitive assistance.

## Release gate
No release as a SparkVisuals in-game client until native loading and at least one harmless data-backed HUD feature are verified in exact target version/device. Current module is a static configurable test overlay, not a full client.
