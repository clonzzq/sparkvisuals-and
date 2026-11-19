# SparkVisuals feasibility check — 2026-11-19

Target: Minecraft Bedrock Android 1.26.50.4. User requirements: in-game client integration; no Resource Packs, Behavior Packs, Add-ons; no fake module switches; no process injection, hooks, protection bypass.

## Verified findings

- Mojang's Bedrock samples version registry lists 1.26.50.4 as current/latest (dated 2026-09-15): https://github.com/Mojang/bedrock-samples/blob/main/version.json
- Official creator update notes for 1.26.50 describe stable Script API releases (including @minecraft/server 2.10.0 and server-ui 2.2.0) and creator tooling. These APIs are provided to creator content, not as a standalone Android client plugin SDK: https://learn.microsoft.com/en-us/minecraft/creator/documents/update1.26.50
- Official changelog likewise discusses server/script and creator APIs: https://www.minecraft.net/en-us/article/minecraft--bedrock-edition-26-50-changelog
- LeviMC/LeviLamina is an open-source Bedrock server-side mod ecosystem, not an Android client mod loader: https://levimc.org/projects/
- The Bedrock protocol docs document network packets and version changes; protocol documentation does not supply an Android client rendering/input mod API: https://mojang.github.io/bedrock-protocol-docs/changelog/
- The BedrockModAPI project surfaced in research explicitly targets Windows and uses game-internals access and DirectX hooks; it is not a compatible or appropriate solution for this Android target: https://github.com/HoneyCombMCBE/BedrockModAPI

## Conclusion / blocker

No verified, supported API/SDK or legitimate Android client-mod interface was found that can add the requested HUD, entity/projectile visuals, item detection, and in-game toggles to the Bedrock Android client while also prohibiting packs/add-ons and process modification. Under these constraints, an actual integrated client is not currently buildable from the repository as it stands.

## Corrective action

Removed the workflow that packaged the native scaffold as a .levipack. That artifact was not an integrated Android client and was contrary to the user's pack/add-on exclusion.

Do not present the companion APK, external overlay, or native scaffold as a game-integrated client. Further work should only claim a real integration after an allowed interface is found and version compatibility is verified. Standalone app work (e.g., persistent waypoint editor) can be implemented and tested separately but is not the in-game client.
