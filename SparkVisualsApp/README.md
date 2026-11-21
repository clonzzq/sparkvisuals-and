# SparkVisuals Android app (prototype)

Standalone Kotlin companion application. **Not an integrated Minecraft client.**

## Included
- Black/crimson dashboard and visual-settings screens.
- Play button launches the official Minecraft Bedrock Android package, if installed.
- HUD preference switches saved locally. They are preferences only and do not render in Minecraft.
- Personal waypoint list with name, X/Y/Z, color, enable/disable, edit/delete and local persistence.
- Manual current X/Y/Z input and horizontal (X/Z) distance calculation to saved waypoints.
- Optional draggable Android system overlay (permission required) showing manually entered coordinates and nearest enabled waypoint distance. It is outside Minecraft and never reads live game data.

## Build APK
GitHub Actions: https://github.com/clonzzq/sparkvisuals-and/actions

The APK is a companion prototype, not the requested in-game client. Installing it cannot activate SparkVisuals modules in Minecraft.

## Hard integration limitation
This project deliberately does not use Resource Packs, Behavior Packs, Creator Add-ons, separately imported native-mod packages, process injection, hooks, patches, or game protection bypass. No public supported API has been verified here that lets this independent APK read Minecraft's live world/player state or render a custom in-game HUD. Therefore game-data-dependent features are not implemented.

**Target:** Minecraft Bedrock Android 1.26.50.4. Exact device/version integration is unverified.
