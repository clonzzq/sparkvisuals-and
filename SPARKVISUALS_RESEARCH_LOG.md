

## 2026-11-22 — protocol-client candidate/version check
- Reviewed the current M9CHKO/bedrock-protocol C++20 library README and its version table. It includes protocol data through Bedrock 1.26.20 (protocol 975), not the target 1.26.50.4. The Mojang protocol reference for the 1.26.50 family reports protocol 2193 for 1.26.50-beta.0; the library's supported table does not include this target family.
- The library is network/protocol infrastructure (client, bot, relay and Android relay-app), not an Android renderer, Bedrock game UI, or integration SDK for the official installed game. Even if its protocol support were updated, it would not supply the in-game HUD or attach to the player's Minecraft session through a supported interface.
- Checked AllayMC Protocol as another maintained Bedrock protocol project; it is protocol library infrastructure, not a standalone Android Bedrock game renderer.
- Reviewed current Starling/Kestrel Android projects: they are Minecraft Java Edition 26.3 clients, not Bedrock. They are not a viable base for target Bedrock 1.26.50.4.
- No implementation/build changes: these candidates do not satisfy the user's no-pack/no-addon/no-injection and integrated Bedrock-game constraints. No new SparkVisuals CI run was triggered; only research documentation was changed.
- Sources: https://github.com/M9CHKO/bedrock-protocol ; https://github.com/M9CHKO/bedrock-protocol/blob/main/documentation/VERSIONS.md ; https://mojang.github.io/bedrock-protocol-docs/1.26.50/ ; https://github.com/AllayMC/Protocol ; https://github.com/starling-client/starling ; https://github.com/NepThunder/Kestrel
