# SparkVisuals Native

Native Android Bedrock mod work-in-progress, separate from the companion APK.

## Current state
- Target: Minecraft Bedrock Android 1.26.50.4; device compatibility remains unverified.
- Candidate runtime: LeviLauncher Android + Preloader SDK 0.2.2, ARM64.
- Native lifecycle registration exists but has not been verified in-game; HUD drawing is not connected.
- Waypoint core supports validated create/edit, enable/disable, delete, lookup, and same-dimension distance calculation.
- Waypoint persistence: versioned quoted format, transactional load.
- **Client settings core added and wired into native mod lifecycle:** settings file loads from the mod config directory, initializes defaults when missing/invalid, and persists on unload. Includes HUD preference fields, crosshair/zoom, scale, and theme colors. Rendering is not connected yet.
- CTest includes waypoint and settings serialization/validation test targets.
- Runtime waypoint editor/HUD and rendering integration are not implemented or verified. No client APK or verified levipack is available yet.

## Unit tests
```sh
cmake -S SparkVisualsNative -B build/native-tests -DBUILD_TESTING=ON
cmake --build build/native-tests
ctest --test-dir build/native-tests --output-on-failure
```

## Android packaging
Requires CMake 3.22+, Ninja, Android SDK/NDK. ARM64 package generation is not verified on device.

## Current implementation boundary
This repository is building out supported native-mod APIs and visual/configuration foundations. No injection, game-memory scanning, patching, protection bypass, or combat automation.
