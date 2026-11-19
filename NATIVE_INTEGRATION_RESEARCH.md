# Native Android Bedrock research — 2026-11-19

## Supported native-mod ecosystem
- LeviLauncher Android: https://github.com/LiteLDev/LeviLauncher-Android — Apache-2.0. Release v1.5.24 notes added native-mod support for Minecraft 1.26.50; current observed release v1.7.1 (2026-11-16). This does not independently verify 1.26.50.4 or any SparkVisuals module.
- Official guide: https://levimc.org/LeviLauncher/android/guide/developer. Android native mods use Preloader SDK (preloader-android) and a separate `pl::` API; use official template https://github.com/QYCottage/levilauncher-android-mod-template.
- Preloader SDK: https://github.com/LiteLDev/preloader-android.
- BedrockTools: https://github.com/QYCottage/BedrockTools, native C++ Android mod for this ecosystem; LICENSE verified MIT; observed v1.5.12 release. Reference only, not a SparkVisuals build.
- Native mod API provides Hook, Signature, Patch etc; official FAQ explains version updates can invalidate mod signatures. This means features requiring game-state access involve native code in the game process and reverse engineering. No such hooking/injection/patch/bypass implementation is provided here.

## Public non-hook HUD drawing API
- Preloader Mod Menu API documents `submitDrawCommands(moduleId, span<DrawCommand>)` and primitives for text, rect, line, circle, triangle, and image. This permits a cosmetic static overlay without reading game state.
- SDK version pinned in this project: `preloader-android` 0.2.2. The pinned header supports draw commands but does not contain the newer HUD editor element API shown in later docs; do not use newer functions until SDK version is upgraded and compatibility verified.
- Reference docs: https://levimc.org/LeviLaunchroid/api/mod-menu
- Draft PR #3 adds a static black/red status panel through the documented draw API. CI and in-game rendering verification are pending.

## SparkVisuals
Target Minecraft Bedrock Android 1.26.50.4. Current app builds, but it is a companion/launcher prototype only. Native code is a lifecycle scaffold; no in-game module or HUD has been tested. No SparkVisuals client APK exists.

## Allowed next steps
Safe cosmetic UI/config, documented non-hook HUD drawing if supported, and compatibility documentation. No item/entity detection, competitive predictions, gameplay automation, or protection bypass.
