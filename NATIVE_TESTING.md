# SparkVisuals Native module: device test checklist

This is a test module, not a finished client. It draws a static panel and exposes configuration only.

## Prerequisites
- Android phone with ARM64 support.
- LeviLauncher Android with native-mod support.
- A separate/isolated Minecraft instance at the target version **1.26.50.4**.
- Latest CI artifact named `SparkVisuals-levipack-arm64`.

## Install and test
1. Download the `.levipack` artifact from the linked successful GitHub Actions run.
2. In LeviLauncher, open the Mods manager for the isolated Minecraft instance and import the `.levipack`.
3. Confirm the SparkVisuals module appears and is enabled.
4. Keep the launcher Mod Menu/HUD overlay enabled, then start Minecraft.
5. Confirm the panel shows **SparkVisuals HUD TEST** and **Game data not connected**.
6. Change panel scale, X and Y sliders; verify the panel moves/scales.
7. Change accent and background colors; verify the panel updates and values persist after restarting.
8. Disable the SparkVisuals module; confirm the test panel disappears. Re-enable and confirm it returns.
9. If the mod crashes or fails to load, disable it and capture the launcher/game log before changing other mods.

## Expected limitation
No coordinates, FPS, ping, inventory, effects, sprint state, target information, item detection or projectile predictions are read from Minecraft. No standalone client APK exists. Do not use this module as evidence of those features being implemented.
