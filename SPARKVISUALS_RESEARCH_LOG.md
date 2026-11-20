# SparkVisuals research log

## 2026-11-20 — follow-up
- Checked LuaClient Mobile v0.1.2 README/status and LICENSE: Apache-2.0. The fork's own compatibility note limits its safe-mode report to Android 15 + Minecraft 1.26.45.x; target 1.26.50.4 is not established.
- Rechecked Xelo-Client (GPL-3.0, launcher-oriented README; in-game menu marked not implemented) and BedrockTools (MIT; native module ecosystem requires LeviLauncher/Preloader and separate package).
- LeviLauncher Android v1.7.1 latest observed release; v1.5.24 release notes added Minecraft 1.26.50 support, not exact patch/device runtime.
- Repository work: clarified root/app README and roadmap; added research checkpoint; removed the workflow that generated a `.levipack` scaffold so it cannot be presented as the requested client. No game integration code was added.
- Verification: GitHub confirmed the deletion commit and main tree no longer contains `.github/workflows/native-android-build.yml`. Existing Android companion CI run 44525792538 succeeded and artifact exists, but it is only the prototype. No build was run for documentation-only changes; no device test.
- Result: still no verified supported path satisfying standalone integrated client + no packs/add-ons/separate mod package + exact 1.26.50.4 + live data. Continue searching for a documented SDK/runtime and preserve the honest release gate.

### References
- LeviLauncher Android releases: https://github.com/LiteLDev/LeviLauncher-Android/releases
- Native-mod guide: https://levimc.org/LeviLauncher/android/guide/developer
- BedrockTools: https://github.com/QYCottage/BedrockTools
- LuaClient Mobile: https://github.com/KauanGitpy/LuaClient-Android
- Xelo-Client: https://github.com/Xelo-Client/Xelo-Client
- SparkVisuals commits: https://github.com/clonzzq/sparkvisuals-and/commits/main
