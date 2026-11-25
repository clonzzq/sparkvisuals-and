# Android companion prototype — manual test checklist

This checklist applies only to `SparkVisualsApp/`, not an integrated client.

## Verified build
- GitHub Actions run #13 (2026-11-21): successful Android debug APK build, artifact `SparkVisuals-Android-prototype` (the contained APK is `SparkVisuals-debug-apk`).
- Source app has not changed since that successful run. Documentation cleanup did not alter Kotlin/Gradle source. A new build was not triggered in this run.
- Artifact link: https://github.com/clonzzq/sparkvisuals-and/actions/runs/44899111516 (retention may expire).

## Manual companion checks
1. Open SparkVisuals and confirm the status labels identify it as a prototype/external HUD.
2. Use PLAY to launch the official Minecraft package (if installed).
3. Grant Android “display over other apps” permission, start the external HUD, and drag it by its title.
4. Enter manual X/Y/Z, save current position, add/edit/color/enable/disable/delete waypoints.
5. Verify local marker data remains after closing/reopening the companion.
6. Verify the HUD shows only manually entered coordinates and nearest enabled waypoint (horizontal X/Z distance), not live game data.
7. Stop/close the HUD and verify its overlay is removed.

## Not tested / not implemented
- No in-game rendering or live game state.
- No FPS, ping, CPS, target, potion/armor/cooldown HUD, item detection, projectile trajectory/prediction, or AutoSprint integration.
- No exact Minecraft version/device integration test.
