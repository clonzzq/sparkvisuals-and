# SparkVisuals Native

Native Android mod prototype; separate from the companion APK.

## Current state
- Intended target: Minecraft Bedrock Android 1.26.50.4; device compatibility is unverified.
- Candidate runtime: LeviLauncher Android + Preloader SDK 0.2.2, ARM64.
- Native lifecycle registration and a prototype Mod Menu entry exist, but have not been verified in-game on the target build.
- Waypoint core supports validated create/edit, enable/disable, delete, lookup, and same-dimension distance calculation.
- Waypoint persistence now has a versioned, quoted text format with transactional loading (invalid files do not overwrite existing state).
- CTest covers validation, duplicate IDs, editing, toggling, deletion, distance, persistence round-trip, and rejection of malformed duplicate-ID files.
- The persistence API is not yet connected to a runtime config directory; no waypoint HUD or in-game editor exists.
- No SparkVisuals client APK or verified levipack exists.

## Run waypoint unit tests
    cmake -S SparkVisualsNative -B build/markers -DBUILD_TESTING=ON
    cmake --build build/markers
    ctest --test-dir build/markers --output-on-failure

## Android build
Requires Windows PowerShell, CMake 3.22+, Ninja, Android SDK and NDK. The build script targets ARM64 and intends to produce dist/arm64-v8a/SparkVisuals.levipack. The Android packaging path remains unverified on a real device.

## Scope
Safe cosmetic UI/configuration and noncompetitive metrics only. No process injection, hooks, game patches, protection bypass, item/entity detection, combat predictions, or gameplay automation.
