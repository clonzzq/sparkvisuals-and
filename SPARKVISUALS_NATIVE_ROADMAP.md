# SparkVisuals Native Client — roadmap

## Goal
Develop SparkVisuals as an in-game native Bedrock Android mod/client UI, not as an Android floating overlay, Behavior Pack, or Resource Pack.

## Target
- Minecraft Bedrock Android: 1.26.50.4 (user-reported; compatibility not verified)
- Android ARM64 (device ABI must be confirmed)
- Visual identity: black / charcoal / crimson red, based on SparkVisuals logo

## Phase 0 — feasibility and compatibility (current)
- [ ] Verify target game's build and Android ABI on a device
- [ ] Choose a supported native mod-loader/launcher platform and verify its licensing and version compatibility
- [ ] Establish a reproducible Android NDK build pipeline
- [ ] Confirm that the mod can be launched and its UI rendered in-game on the target build

## Phase 1 — minimal native in-game HUD
- [ ] In-game module UI (black/red theme)
- [ ] FPS display, using a reliable in-game frame timing source
- [ ] Ping display, using a reliable connection latency source; show unavailable rather than fabricate values
- [ ] CPS display, based on legitimate input events
- [ ] Coordinates, only when accessible through supported mod interfaces
- [ ] Persist module settings

## Phase 2 — polish and additional modules
- [ ] HUD editor and layout persistence
- [ ] Animations, Zoom, crosshair customization
- [ ] Target information only where exposed through legitimate interfaces
- [ ] Compatibility testing and version checks

## Important status
This file is a roadmap only. No native Minecraft integration, FPS/ping telemetry, in-game HUD, or compatibility with Bedrock 1.26.50.4 has been implemented or tested yet. The existing Android APK is a separate UI prototype.
