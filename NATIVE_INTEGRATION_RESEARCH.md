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
- Static black/red status panel source now integrated in main; ARM64 compile/package passed on PR run 44519229999. In-game rendering remains unverified.

## SparkVisuals
Target Minecraft Bedrock Android 1.26.50.4. Current app builds, but it is a companion/launcher prototype only. Native code is a lifecycle scaffold; no in-game module or HUD has been tested. No SparkVisuals client APK exists.

## Allowed next steps
Safe cosmetic UI/config, documented non-hook HUD drawing if supported, and compatibility documentation. No item/entity detection, competitive predictions, gameplay automation, or protection bypass.

## Research update — 2026-11-20 (verified)
- LeviLauncher Android release notes show v1.5.24 added support for Minecraft 1.26.50 (release date 2026-09-16). This is version-family support, not proof of exact patch 1.26.50.4 on a device: https://github.com/LiteLDev/LeviLauncher-Android/releases
- Current official docs describe Android native mods as loaded through the launcher’s Preloader SDK. The SDK provides lifecycle, configuration, input, Mod Menu, and drawing-related APIs, but the FAQ says it does not expose a public game-state API; state-dependent access would require game-internal signatures/hooks. Under the user's no-injection/no-hook constraint, this is not an applicable route for a full client: https://levimc.org/LeviLauncher/android/guide/developer and https://levimc.org/LeviLauncher/faq/play/android/usage/
- The official Minecraft Creator APIs documented for 1.26.50 are APIs for creator content, not a standalone external client API. Using them would require packs/add-ons, which the user explicitly forbids: https://learn.microsoft.com/en-us/minecraft/creator/documents/update1.26.50
- A separate Android app can technically draw an application overlay, but it is not an in-game client or a data-connected HUD. Android 12+ can hide overlays and blocks some untrusted touch-through interactions; therefore this is not a suitable replacement for the requested integrated client: https://developer.android.com/about/versions/12/features and https://developer.android.com/about/versions/12/behavior-changes-all
- CI verified the waypoint-core build/tests (run 44514320765) and Android prototype/debug APK builds (runs 44525792538 and 44525792379). These verify compilation/packaging only; they do not establish game loading or in-game rendering. No device test or Minecraft runtime compatibility test was available.
- No code changes made this research pass: there is no newly verified safe implementation route that satisfies all requirements (integrated in-game client, no packs/add-ons, no process injection/hooks, target 1.26.50.4).
