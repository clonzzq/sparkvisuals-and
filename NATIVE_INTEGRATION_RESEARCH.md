# Native Android Bedrock research — 2026-11-20

## What is verified
- **LeviLauncher Android:** https://github.com/LiteLDev/LeviLauncher-Android. Apache-2.0. Release v1.5.24 (2026-09-16) added Minecraft 1.26.50 support; v1.7.1 was the latest observed release (2026-11-16). This is version-family support only; no exact 1.26.50.4 device test.
- **Preloader native-mod SDK:** https://levimc.org/LeviLauncher/android/guide/developer and https://github.com/LiteLDev/preloader-android. Native modules execute in the game runtime and are loaded via LeviLauncher. Public APIs cover lifecycle/config/input/menu/drawing, but no public live game-state API was verified.
- **BedrockTools:** https://github.com/QYCottage/BedrockTools. MIT license checked; v1.5.12 observed (2026-11-07). Native C++20 ARM64 modules, 51 modules in current README; requires LeviLauncher/Preloader and separate native-mod package. Reference only; not adopted.
- **LuaClient Mobile:** https://github.com/KauanGitpy/LuaClient-Android. v0.1.2 public fork, Apache-2.0; own README/status describes launcher, native core, UI modules and local overlays. Its status warns that Android 15 + Minecraft 1.26.45.x uses safe mode and additional native modules are unavailable. Exact target version unknown/unverified.
- **Xelo-Client:** https://github.com/Xelo-Client/Xelo-Client. GPL-3.0; Android launcher/client repository. README feature list is launcher-oriented and marks in-game mod menu as not implemented; compatibility with 1.26.50.4 is not verified.

## Excluded integration routes
- Creator Script API for Bedrock 1.26.50 is not a standalone Android client SDK; packs/add-ons are forbidden by user.
- Native-mod loader/package workflows do not meet the user's standalone integrated-client requirement.
- External Android overlay is not in-game and has no live Minecraft data source.
- No process injection, hooks, signature scanning, patches, protection bypass, or competitive automation will be implemented.

## SparkVisuals status
Companion APK and native scaffold are not the client. Core marker/settings tests and previous CI compilation/package checks are evidence only for those isolated components. The in-game runtime, target version, and any HUD/data feature have not been demonstrated.

## Conclusion / next research
No route verified so far satisfies all of: standalone integrated APK, no packs/add-ons or separately imported mod package, exact Minecraft 1.26.50.4 compatibility, and live game-data access. Continue checking for a documented supported client SDK/runtime; do not misrepresent an external overlay or launcher as a client.
