# SparkVisuals Native

This is the native Android mod prototype, separate from the abandoned APK shell.

## Target / current state
- Minecraft Bedrock Android **1.26.50.4** is the intended target; compatibility is unverified until tested on-device.
- ARM64 (`arm64-v8a`), LeviLauncher Android + Preloader SDK 0.2.2.
- C++ native lifecycle registration and one prototype Mod Menu entry are implemented.
- No gameplay HUD rendering, telemetry integration, or waypoint UI is implemented yet.
- No APK or compiled `.levipack` is checked into this repository.

## Build prerequisites
Windows PowerShell, CMake 3.22+, Ninja, Android SDK and Android NDK. The build script uses `ANDROID_HOME` / `ANDROID_SDK_ROOT` and `ANDROID_NDK_HOME` / `ANDROID_NDK_ROOT`, or explicit paths.

Run from this folder:
```powershell
./build.ps1 -Clean
# or:
./build.ps1 -Sdk "C:/Android/Sdk" -Ndk "C:/Android/Sdk/ndk/<version>"
```
The script targets ARM64 and is intended to produce `dist/arm64-v8a/SparkVisuals.levipack`. This packaging script has not yet been run in a real Android NDK environment, so treat it as unverified.

## Safe planned scope
Visual HUD / UI, user-configured coordinates and waypoints, customization and noncompetitive metrics. No auto-play, anti-cheat evasion, or hidden-player/object detection features.
