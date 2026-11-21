# SparkVisuals Native

Native Android Bedrock prototype source, separate from the companion APK.

## Current state
- Intended target: Minecraft Bedrock Android 1.26.50.4; device compatibility is unverified.
- Candidate runtime: LeviLauncher Android + Preloader SDK 0.2.2, ARM64.
- Source registers a native lifecycle mod and contains a cosmetic status-panel draw-command call using the documented Mod Menu API. It displays a test label and explicitly states that game data is not connected.
- The panel source includes scale, position, and color configuration. This is source-level only: native SDK compilation and Minecraft on-device rendering are not verified.
- Waypoint/settings core supports validated persistent user data; CTest includes host tests.
- No live game telemetry, runtime waypoint editor, or data-dependent HUD is implemented.
- No APK or native mod package is produced or accepted as the requested deliverable.

## Host unit tests
```sh
cmake -S SparkVisualsNative -B build/native-tests -DBUILD_TESTING=ON
cmake --build build/native-tests
ctest --test-dir build/native-tests --output-on-failure
```
The current core test cases were reproduced on host and passed (2/2). This does not compile the Preloader SDK integration.

## API research
The official Preloader Mod Menu API documents cosmetic HUD draw commands, font/image registration, movable HUD-editor elements, and HUD surface size:
https://levimc.org/LeviLamina/developer_guides/android/api/mod_menu/
Using this API still requires a native module loaded by LeviLauncher/Preloader, which is excluded from the user's strict standalone/no-separate-mod/no-injection deliverable.

## Safety / release boundary
No injection, hooks, signature scanning, patches, protection bypass, game-data access, competitive automation, or separate native package will be built or presented as a client. A client release requires exact target-version/device runtime proof.
