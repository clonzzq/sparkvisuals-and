# SparkVisuals Android app (prototype)

Standalone Kotlin Android application scaffold for SparkVisuals. This is **not yet an integrated Minecraft client**.

## Included
- Black/crimson dashboard and visual-settings screens.
- Play button launches the official Minecraft Bedrock Android package, if installed.
- HUD preference switches (FPS/CPS/coordinates/effects/armor/cooldown/crosshair/zoom/layout) saved locally. These are configuration preferences only and are not rendered in Minecraft.
- Personal waypoint list: add named X/Y/Z coordinates, delete markers, persist locally.
- Manual horizontal-distance calculator: enter current X/Z and see distance to saved waypoints.

## Build APK
The GitHub Actions workflow at `.github/workflows/sparkvisuals-android.yml` builds a debug APK after changes to the app or workflow, and also supports manual runs.

1. Open the repository Actions tab: https://github.com/clonzzq/sparkvisuals-and/actions
2. Open the **SparkVisuals Android APK** run.
3. If it succeeds, open its Artifacts section and download **SparkVisuals-debug-apk**.
4. Extract the ZIP and install `app-debug.apk` on Android. Android may ask to allow installation from the file manager/browser.

The APK build is **not verified yet** until an Actions run completes successfully and the artifact is available.

## Integration limits
This project does not inject, hook, patch, or replace Minecraft libraries. It does not draw custom HUD elements in-game. It is a standalone companion app prototype; any future in-game functionality must use supported Minecraft resource-pack/add-on APIs.

## Target
Minecraft Bedrock Android 1.26.50.4; server reference: `sparktime.fun`. Server connection is not implemented; Play launches the game itself.
