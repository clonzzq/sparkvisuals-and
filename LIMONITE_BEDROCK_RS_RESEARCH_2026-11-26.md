# Limonite + bedrock-rs research (2026-11-26)

## LimoniteMC/limonite-client — real Bedrock client, but excluded

Primary repository: https://github.com/LimoniteMC/limonite-client

- Rust client from scratch, with a fork of Pumpkin embedded for single-player.
- GPL-3.0; workspace manifest states edition 2024 and Rust 1.97.
- Current public README says milestone 5 (playable single-player), under heavy construction, no releases. It lists world selection, walking/flying, block breaking/placing, inventory movement, basic mobs, respawn, commands, and HUD.
- README's ground rules: vanilla textures are read at runtime from the user's own Bedrock install; block models are read from the user's own Java Edition jar. The client uses a `packs` workspace crate to load vanilla resources. These assets violate the user's strict no-resource-pack/assets requirement, so the candidate is disqualified even if assets are not distributed by the project.
- Single-player only: it joins the embedded server through an in-memory transport. LAN is a future milestone; public servers/Realms are explicitly not supported.
- Repository tree has no Android/Gradle/JNI/mobile packaging paths and no Android build/release artifacts. Android portability of winit/wgpu alone would not prove an app port.
- Protocol target/device compatibility with Bedrock 1.26.50.4 is not verified.

Verdict: useful evidence that a clean-room Bedrock client can be implemented, but not a viable base for SparkVisuals under the current constraints. No code copied, forked, built, or tested.

## bedrock-crustaceans/bedrock-rs — protocol toolkit only

Primary repository: https://github.com/bedrock-crustaceans/bedrock-rs

- Apache-2.0, Rust.
- Workspace contains protocol, networking (batching, compression, encryption, RakNet), auth, level, and forms components; add-on support is a separate optional family.
- The current tree includes an explicit generated protocol crate `bedrock_network_version_v2193`, which corresponds to stable Bedrock 1.26.50's protocol number. The latest repository commit adds 1.26.70 support, and its example client currently selects V2259. This proves a version-specific protocol layer exists, not that 1.26.50.4 compatibility or Android runtime is tested.
- No renderer, game loop, Android app entrypoint, touch controls, APK workflow, or in-game HUD is provided. The repository tree contains no Android packaging path.
- It is a toolkit for a separate protocol client; it is not an API for reading or rendering live data inside the installed official Minecraft app.

Verdict: a potentially reusable protocol-only component for a separately developed client after an independent license/dependency/version audit, but it does not solve the game client. No source copied or build attempted. Add-on feature is not required for protocol/network features, but no use is planned under the current project constraints.

## Falcon-MC/Kestrel search result

Search indexing describes a C++20 native Bedrock client with desktop rendering and textures sourced from an installed game or entitled vanilla pack. Direct GitHub repository/API requests to https://github.com/Falcon-MC/Kestrel returned 404 during this run. Its source, license, Android support, and exact target are therefore unverified; do not use or cite it as an actionable candidate.

## Other current search results ruled out

- Torch Client and Pomme: Java Edition clients, not Bedrock.
- Starling/Kestrel Android ports in the search results: Java Edition, not Bedrock.
- Lumen: a modified Linux Bedrock launcher/client and mod ecosystem; no Android standalone client or no-pack integration verified.

## Constraint conclusion

The target is still: Bedrock Android 1.26.50.4; no Resource Packs, Behavior Packs, Creator Add-ons, separately imported mods, or unsupported process modification. Current official Creator documentation describes pack/add-on APIs, not a supported stand-alone client HUD API. No public supported interface has been verified that lets an independent companion APK read the official game's live player/world state or render inside it.

The technically honest paths remain:
1. Research a clean-room independent client only if it can meet the no-pack asset rule, Android port, touch UI, exact protocol/content compatibility, and license gates; this is a very large project.
2. Keep the companion limited to local settings/waypoints/manual coordinates, clearly labeled as external.
3. Do not implement or claim game telemetry, in-game HUD, item detection, trajectory predictions, or gameplay automation under the excluded integration routes.

## Sources checked

- Limonite README, client manifest, license, tree, commits: https://github.com/LimoniteMC/limonite-client
- bedrock-rs README, Apache license, v2193 crate and example: https://github.com/bedrock-crustaceans/bedrock-rs
- Stable 1.26.50 protocol reference: https://mojang.github.io/bedrock-protocol-docs/1.26.50/
- Creator/resource pack documentation: https://learn.microsoft.com/minecraft/creator/documents/resourcepack
- Falcon-MC/Kestrel direct primary lookup (404): https://github.com/Falcon-MC/Kestrel

No APK, code, tests, or build were produced in this research run.
