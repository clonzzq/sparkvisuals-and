# SparkVisuals Native

Native Android mod prototype; separate from the abandoned APK shell.

## Current state
- Intended target: Minecraft Bedrock Android 1.26.50.4; device compatibility is unverified.
- ARM64, LeviLauncher Android + Preloader SDK 0.2.2.
- Native lifecycle registration and a prototype Mod Menu entry exist.
- Waypoint core supports validated create/edit, enable/disable, delete, lookup, and same-dimension distance calculation.
- CTest unit tests cover validation, duplicate IDs, editing, toggling, deletion, and distance.
- No gameplay HUD rendering, telemetry integration, persistence adapter, or in-game waypoint UI exists yet.
- No APK or compiled levipack is checked in.

## Run waypoint unit tests
    cmake -S SparkVisualsNative -B build/markers -DBUILD_TESTING=ON
    cmake --build build/markers
    ctest --test-dir build/markers --output-on-failure

## Android build
Requires Windows PowerShell, CMake 3.22+, Ninja, Android SDK and NDK. The build script targets ARM64 and intends to produce dist/arm64-v8a/SparkVisuals.levipack. The Android packaging path remains unverified on a real device.

## Scope
Visual HUD / UI, user-configured coordinates and waypoints, customization and noncompetitive metrics. No auto-play, anti-cheat evasion, or hidden-player/object detection features.