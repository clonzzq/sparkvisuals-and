# SparkVisuals feasibility check — 2026-11-19

Target: Minecraft Bedrock Android 1.26.50.4. Requirements: in-game integrated client; no Resource Packs, Behavior Packs, Add-ons; no fake switches.

## Android precedent (newly verified)
- Flarial's official Play listing (updated 2026-09) claims Android release with modules, ClickGUI, themes, keybinds: https://play.google.com/store/apps/details?id=xyz.flarial.client
- Public Flarial client repo says Windows 10/11 x64 support and does not document Android internals: https://github.com/flarialmc/dll-oss
- Atlas official Play listing calls it a launcher requiring Minecraft from Play Store and advertises minimap, zoom, FPS, CPS, speed, keystrokes, auto-sprint, view-model/environment controls: https://play.google.com/store/apps/details?id=net.atlasclient.atlaspe
- Atlas official site claims 60+ customizable mobile modules: https://atlasclient.net/
- Atlas public GitHub organization has no public client source; it has a Dobby hook-framework fork, not client source: https://github.com/AtlasClientMC

These prove Android Bedrock clients exist. They do not provide a public, verified, reusable client SDK or source implementation for SparkVisuals.

## Platform documentation
- Bedrock version registry: https://github.com/Mojang/bedrock-samples/blob/main/version.json
- Creator Script API docs (for creator content, not standalone Android client mods): https://learn.microsoft.com/en-us/minecraft/creator/documents/update1.26.50
- Bedrock changelog: https://www.minecraft.net/en-us/article/minecraft--bedrock-edition-26-50-changelog
- LeviMC/LeviLamina is server-side mod ecosystem: https://levimc.org/projects/
- Protocol docs: https://mojang.github.io/bedrock-protocol-docs/changelog/
- BedrockModAPI targets Windows: https://github.com/HoneyCombMCBE/BedrockModAPI

## Conclusion
Earlier claim that Android client mods were simply unavailable was too broad: Flarial and Atlas have public Android releases. What remains missing is a public, supported, reusable integration SDK/source or legal technical route we can verify for this project without copying proprietary internals or implementing unsupported process modification. Repo currently has no such integration.

## Corrective actions / next steps
- Removed workflow packaging the native scaffold as .levipack; it violated pack/add-on exclusion and was not an integrated client.
- Continue checking official integration/licensing options, open Android launcher/runtime projects and public SDK interfaces, verifying licenses and exact version compatibility.
- Never present launcher-only, overlay-only, or scaffold work as a game-integrated client. Only claim integration after source/build/version verification.


## New open-source Android-native candidates (verified 2026-11-19)
- Xelo-Client is an open-source Android Bedrock client/launcher project with launcher-level features and an in-game mod menu; its repo is GPL-3.0. This is evidence of an Android client ecosystem, but not a turnkey SparkVisuals implementation or proof of compatibility with 1.26.50.4: https://github.com/Xelo-Client/Xelo-Client
- BedrockTools is a GPL-3.0 native C++20 Android mod for LeviLauncher/Preloader, advertises HUD/player/visual modules (including ArmorHUD, PotionHUD, coords, AutoSprint, Zoom), and provides a public SDK/event system. Its README says Android 9+, ARM64 and requires LeviLauncher plus a .levipack import: https://github.com/QYCottage/BedrockTools
- This native-mod route is NOT Resource Pack / Behavior Pack / Creator Add-on, but it does require a third-party launcher/mod-loader and a native mod package. Therefore it does not yet satisfy a stricter “standalone client APK with nothing separately imported” interpretation. Target-game 1.26.50.4 compatibility is not verified in public README/release data.
- A LeviMC tutorial documents Android native mods and says build prerequisites include Android SDK/NDK, CMake and Ninja; this confirms the native extension ecosystem exists, not a supported Mojang SDK: https://levimc.org/tutorials/develop/first-android-mod/

## Practical decision
Investigate whether LeviLauncher/Preloader publicly supports target 1.26.50.4 and whether GPL-3.0 BedrockTools can legally/technically be forked as a separate SparkVisuals native module. Do not copy proprietary client code. Before any code is claimed as functional, verify exact release/game version, build success, and runtime behavior. If the native-mod package/loader is unacceptable under the user's constraint, stop that route and do not disguise it as standalone integration.


## Version support evidence (additional verification)
- LeviLauncher Android releases include an explicit v1.5.27 note: “LeviLauncher 1.26.50 Support” and “Added inbuilt mod support for Minecraft 1.26.50.” The current docs state Android ARM64 and Minecraft 1.21.80+; newer launcher releases continue updating supported MC versions. This is evidence that the launcher ecosystem supports the target version line, not proof that BedrockTools itself works on 1.26.50.4: https://github.com/LiteLDev/LeviLauncher-Android/releases
- The official compatibility docs explain that native mods are separate from Creator add-ons and that native mods must be version-matched. They run native code in the game process. This architecture may meet the no-RP/no-BP/no-Creator-Add-on constraint, but it requires a loader/runtime. Keep the distinction explicit: the current SparkVisuals launcher APK is not integrated and no native SparkVisuals build has been tested.
- Candidate implementation strategy for further exploration: bundle a licensed-compatible native SparkVisuals module and its runtime into a customized launcher/client distribution so users do not manually import a pack; this is still a launcher + native-mod architecture, not an independently modified Minecraft APK. This remains research only until licensing, integration boundaries, build and target-version runtime are verified.
