# SparkVisuals Android — development repository

**Target:** Minecraft Bedrock Android 1.26.50.4.

This repository is being developed toward a real in-game client. The checked-in Android APK is currently only a standalone UI prototype: it does not render an overlay or modify Minecraft. Do not treat it as the finished client.

## Build the Android prototype

The Gradle project is in `SparkVisualsApp/`. GitHub Actions builds that checked-in project directly:

- Workflow: [Build SparkVisuals Android prototype](.github/workflows/android-apk.yml)
- Build command: `gradle --no-daemon clean assembleDebug`
- Output: `SparkVisualsApp/app/build/outputs/apk/debug/app-debug.apk`
- Workflow artifact: `SparkVisuals-Android-prototype`

The workflow was corrected in commit `3fa2373` to stop extracting and building a stale ZIP archive.

## Native module research

- [Client architecture and feasibility](CLIENT_ARCHITECTURE.md)
- [Native integration research](NATIVE_INTEGRATION_RESEARCH.md)
- [Native client roadmap](SPARKVISUALS_NATIVE_ROADMAP.md)

The native directory contains a lifecycle/configuration experiment and waypoint/settings storage with passing core CI. CI has produced an ARM64 `.levipack` containing an Android AArch64 shared library. The module has a loader settings catalog, but no in-game HUD rendering or runtime load has been verified.

## Native build artifact

- Verified ARM64 native module package (`.levipack`) from CI run [44519001880](https://github.com/clonzzq/sparkvisuals-and/actions/runs/44519001880) (artifact retention: 7 days). It contains the module settings catalog but is not a standalone APK and has not been loaded in-game.
- Draft [PR #3](https://github.com/clonzzq/sparkvisuals-and/pull/3) adds a cosmetic static HUD test panel using documented draw commands; awaiting build verification.

## Current honest status

- Android UI prototype: source present; latest direct Gradle workflow has not yet been verified.
- Native core tests: **passed** on commit `c0f4efc`.
- Native ARM64 build: **passed**; `.levipack` artifact produced and ELF verified as AArch64 Android.
- Loader module settings catalog: coordinates, FPS/ping, sprint, potion, armor, CPS, crosshair, zoom (settings only; no render implementation).
- Native source menu status update: draft PR #2 awaiting CI.
- Minecraft integration: not proven.
- Exact Minecraft 1.26.50.4 device test: not done.
- Verified SparkVisuals in-game client APK: **does not exist yet**.

## Product boundary

No resource packs, behavior packs, creator add-ons, fake in-game toggles, process injection, game hooks/patches, protection bypasses, or competitive gameplay automation. Any feature described as working in-game must first be demonstrated in Minecraft on the target version.
