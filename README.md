# SparkVisuals Android — development repository

**Target game:** Minecraft Bedrock Android 1.26.50.4  
**User requirement:** an actual integrated client, not a companion app; no Resource Packs, Behavior Packs, Creator Add-ons, or separately imported mod packages.

## Honest status

- `SparkVisualsApp/` is a standalone Android companion/UI prototype. It can launch Minecraft and show a draggable external preview overlay. Its FPS/coordinates are placeholders; it does not read game state or draw inside Minecraft.
- `SparkVisualsNative/` is experimental native source and testable settings/waypoint core. It is not a verified integrated client. It has not been runtime-tested against the target game/device.
- The former workflow that packaged the native scaffold as a `.levipack` has been removed because that package is not the requested standalone client deliverable.
- No SparkVisuals in-game client APK exists. No module is claimed to work in-game.

## Repository

- Android prototype: `SparkVisualsApp/`
- Experimental native source and host tests: `SparkVisualsNative/`
- Android prototype CI: `.github/workflows/android-apk.yml`
- Native core tests: `.github/workflows/native-core-tests.yml`

## Research and next steps

- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Native client roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)
- [On-device test checklist](NATIVE_TESTING.md)
- [Android feasibility research](SPARKVISUALS_ANDROID_FEASIBILITY_2026-11-19.md)

Open-source Android launcher/client projects are research references only. A project requiring a separate native-mod loader/package is not accepted as the final SparkVisuals deliverable. The new LuaClient Mobile fork is an interesting Apache-2.0 launcher base, but its own compatibility/status report is for Minecraft 1.26.45.x, not the exact target 1.26.50.4; compatibility is unverified.

## Scope and safety boundary

Cosmetic UI, configuration, persistent personal waypoint data, and benign overlays are the only implementation areas considered here. Game-state-dependent item/entity detection, projectile predictions, competitive helpers, gameplay automation, undocumented hooks, process injection, signature scanning, patching, and protection bypass are not implemented.

A feature may be called functional only after it is built and demonstrated on the exact Minecraft version/device. Do not publish a prototype or loader package as a client.
