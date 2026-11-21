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


## 2026-11-21 — companion overlay improvement
- Audited the current Android app source: marker CRUD and local storage were already real; module toggles remain preference flags only. Existing floating HUD was static placeholder text.
- Implemented manual current X/Y/Z entry and save. Waypoint distances in the app remain horizontal X/Z only.
- Reworked the external overlay to read saved manual coordinates and waypoint JSON, show the nearest enabled waypoint's horizontal distance, and refresh locally every 2 seconds. It still does not read Minecraft process/game state and is not an in-game client.
- Updated app README to describe this capability honestly. Android CI will verify compilation; no Android device or exact Minecraft 1.26.50.4 runtime test is available.
- Research update: official creator HUD APIs are pack/add-on APIs and excluded by the user's constraint; official Minecraft deep links are documented as internal/unstable and are not a HUD SDK. No supported standalone client SDK found.
- References: https://learn.microsoft.com/en-us/minecraft/creator/documents/update1.21?view=minecraft-bedrock-stable ; https://github.com/MicrosoftDocs/minecraft-creator/blob/main/creator/Reference/Content/deep-links.md


## 2026-11-21 — candidate review
- Found Flarial Android as an existing separate client app on Google Play. Release listings indicate recent Bedrock 26.50/26.51/26.52 support, but exact 1.26.50.4 and device compatibility are not verified. Android APK source/license not publicly identified. The desktop public AGPL-3.0 repository says portions are private and its build instructions are Windows-specific; not a source base for SparkVisuals.
- Reviewed the C++ Bedrock protocol library M9CHKO/bedrock-protocol at a high level. It is networking/protocol infrastructure, not a renderer or supported game-client HUD SDK, so it cannot replace the existing game for this project.
- New research does not produce a supported integration path under the no-packs/no-addons/no-unsupported-game-modification constraints.
- Sources: https://flarial.xyz/ ; https://play.google.com/store/apps/details?id=xyz.flarial.client ; https://github.com/flarialmc/dll-oss ; https://github.com/M9CHKO/bedrock-protocol
