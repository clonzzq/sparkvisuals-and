# SparkVisuals Android app (prototype)

Standalone Kotlin Android application scaffold for SparkVisuals. This is **not yet an integrated Minecraft client**.

## Implemented in the app source
- Black/crimson dashboard and visual settings screens.
- Play button opens the official Minecraft Bedrock Android package if installed.
- HUD preference switches (FPS/CPS/coordinates/effects/armor/cooldown/crosshair/zoom/layout) persist locally in Android preferences. They are settings only; not rendered in-game.
- Personal waypoint list: add named X/Y/Z coordinates, delete markers, and persist them locally.
- Manual distance calculator: enter your current X/Z and see horizontal distance to saved waypoints.

## Build
GitHub Actions workflow `.github/workflows/sparkvisuals-android.yml` is set to build a debug APK when project files change and upload `SparkVisuals-debug-apk`. **APK build remains unverified** until a successful workflow run is accessible.

## Integration limitation
This project does not inject, hook, patch, or replace Minecraft libraries. It does not draw custom HUD elements in-game. The app is a starting point for standalone UI and benign, supported resource-pack/add-on integration, not a Flarial-equivalent client.

## Target
Minecraft Bedrock Android 1.26.50.4; server reference: `sparktime.fun`. Server connection is not implemented; Play launches the game itself.
