# Native Android Bedrock client research — 2026-11-19

- LeviLauncher Android repository: https://github.com/LiteLDev/LeviLauncher-Android (Apache-2.0). Current release observed v1.7.1 (2026-11-16); supports importing licensed game APK and native modules. Compatibility with MC 1.26.50.4 not yet independently tested.
- BedrockTools: https://github.com/QYCottage/BedrockTools. README describes native C++20 Android mod for LeviLauncher/Preloader, SDK/event system, HUD modules, ARM64/Android 9+. LICENSE file verified as MIT (not GPL). Release v1.5.12 (2026-11-07). Version support target must be confirmed; source is reference only.
- Neither project is SparkVisuals and neither has been integrated. Avoid distributing their module packages as our product or copying code without proper license/attribution review.
- Current SparkVisuals APK is companion/launcher prototype only. No in-game module loading or HUD feature is verified.
- User target: Minecraft Bedrock Android 1.26.50.4. Need test with licensed game/runtime/device. No anti-cheat bypass, protection evasion, or unsupported process-modification instructions.
