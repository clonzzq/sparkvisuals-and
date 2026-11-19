# SparkVisuals Android app (prototype)

An independent Android application prototype. This is **not yet an integrated Minecraft client**.

## What currently exists
- Standalone Android project (Kotlin / Android Gradle Plugin).
- Black/red launcher screen and Play button.
- Play opens the official Minecraft Bedrock app if it is installed under the standard Android package.
- Three preference toggles saved locally (crosshair, coordinates, potion HUD). These preferences are **not connected to in-game rendering**.
- GitHub Actions workflow builds a debug APK artifact.

## Build
The GitHub Actions workflow `.github/workflows/sparkvisuals-android.yml` runs on changes to this folder and can be manually triggered from Actions. It builds a debug APK, then uploads it as an artifact. Build is not yet verified until a workflow run succeeds.

## Integration limitation
This launcher project does not inject, hook, patch, or replace Minecraft libraries. It does not draw HUD elements inside Minecraft. The APK is an honest starting point for a standalone app and supported integration work, not a Flarial-equivalent client.
