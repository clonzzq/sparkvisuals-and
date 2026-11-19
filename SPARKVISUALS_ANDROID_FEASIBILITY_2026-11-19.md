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
