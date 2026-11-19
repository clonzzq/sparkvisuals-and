# SparkVisuals native-client roadmap

## Priority
Build the actual Android Bedrock in-game client; not companion UI or launcher shell.

## Research completed
- [x] Existing Android APK and native stub audited; neither is an in-game client.
- [x] Native Android client ecosystem researched.
- [x] LeviLauncher Android v1.5.24 release notes explicitly add native-mod support for MC 1.26.50; current observed v1.7.1.
- [x] Official Android native mod guide, template, and Preloader SDK identified.
- [x] BedrockTools MIT license verified from LICENSE file.
- [x] Exact target 1.26.50.4 is not device-tested.

## Safety/technical boundary
Android native-mod API supports Hook/Signature/Patch capabilities and native code inside game process. No process injection, hooks/signature scans/patches, protection bypass, or competitive gameplay automation will be implemented. This restricts client features that require game internals. Continue only benign UI/config/HUD drawing using documented APIs without hooks.

## Latest verified progress (2026-11-19)
- [x] Native ARM64 CI compile/link succeeded after linking Android `log` library.
- [x] Core marker/settings unit tests passed on commit `c0f4efc`.
- [x] `.levipack` packaging completed; extracted ELF verified as AArch64 Android shared library (Android 28).
- [x] Loader settings catalog exposed (coordinates, FPS/ping, sprint, potion, armor, CPS, crosshair, zoom); these are configuration-only and do not render.
- [ ] Rebuild/CI verification for latest menu-description update is running on draft PR #2.

## Safe work items
- [ ] Create black/red in-game UI concept and module catalog without pretending modules are functional.
- [ ] Add durable config schema and settings persistence.
- [ ] Verify public non-hook HUD drawing API with a minimal test in supported runtime.
- [ ] Test exact target build on a real device if available.
- [ ] Build/release only verified artifacts and state dependencies accurately.

## Out of scope
Item/entity detection, projectile predictions, trap/scrap/sugar detection, gameplay automation, aim or other competitive assistance.

## Release gate
No release as SparkVisuals client until in-game load and real harmless HUD feature are verified on exact MC version and device.
