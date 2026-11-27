# SparkVisuals — client architecture and feasibility (2026-11-25)

## Goal and hard constraints
A real standalone Android client for Bedrock 1.26.50.4, not a companion/launcher/overlay. User excludes Resource Packs, Behavior Packs, Creator Add-ons, separately imported mod packages, process injection/hooks, patches, and protection bypass.

## Current project state
- `SparkVisualsApp/` is a companion prototype only. It launches Minecraft, saves personal waypoints, and displays an external overlay using manual coordinates.
- Switches in the companion are preference flags; they do not affect Minecraft.
- No SparkVisuals integrated client APK exists.
- Separate clean-room Rust core exists at `client/core/`: lifecycle, RakNet ping/pong and offline Open Connection exchange only. GitHub Actions run #15 passed 15/15 host tests, including loopback mocks; no real-server/device validation. It still has no reliability layer, login/authentication, world decoder, renderer, touch UI or Android packaging.
- The previous `SparkVisualsNative/` in-process mod scaffold and host-test workflow were deleted 2026-11-25. They were not a standalone client and contradicted the restrictions.

## Candidate scan
- **Acacia viewer (MIT):** https://github.com/acacia-rs/acacia — Rust workspace contains protocol/network, world state, wgpu renderer, HUD/menu UI, and an interactive Bedrock viewer/client-like app using winit 0.30 and wgpu 30. Target 1.26.51/protocol 2193, same stable wire protocol number as 1.26.50; exact 1.26.50.4 compatibility is not verified. Upstream winit 0.30 Android backend and wgpu 30 Vulkan support Android, but Acacia has no Android entrypoint/glue enabled, APK or device evidence, and its viewer is keyboard/mouse-only (no touch UI). Renderer fetches/bakes vanilla resource-pack assets, so strict no-resource-pack policy disqualifies it unless clarified. Highest architecture candidate; research only.
- **Cinnabar:** https://github.com/cinnabar-mc/cinnabar — independent Bedrock client with experimental Android APK, but target 1.26.60/protocol 2225; PolyForm Noncommercial and EULA-gated vanilla resource assets. Version and asset-policy mismatch.
- **Zuri:** https://github.com/zuri-mc/zuri — MIT, incomplete Bedrock client; Android target/build and exact protocol are not documented.
- **CaramelClient:** https://github.com/ChimeraAnt-DEV/CaramelClient — Apache-2.0 Android launcher that imports the official game APK and loads native mods; not its own game engine and excluded.
- **Limerence/Selaura:** https://github.com/LimerenceMC/client — GPL-3.0 Bedrock modification framework; Android support listed, but in-process client modification path excluded.
- **BedrockTools:** https://github.com/QYCottage/BedrockTools — real native Android in-game HUD/mod, but requires a mod loader/process modification and separate .levipack; excluded. Upstream license statements conflict (GPL-3.0 README vs MIT repository metadata/LICENSE), so no source reuse.
- **Torch (AGPL-3.0):** https://github.com/torch-client/torch-client — real Rust standalone Android client preview with touch controls and ClickGUI; supported Java Edition versions only (26.1.1/26.2/1.21.11), not Bedrock. Research UI architecture only; no code reuse.

## Feasibility boundary
Official Bedrock Android does not provide a verified public API here for a separate app to read live world/player state and draw an in-game HUD. Native-loader paths examined modify the game process and are excluded. A standalone reimplementation is a different project: network protocol, world state, renderer, controls, assets, auth, and Android packaging. No candidate has been ported or tested on Android for this project.

## Next research
1. Verify upstream Android portability at a high level for Acacia's winit/wgpu viewer (no fork/build until constraints clear).
2. Confirm exact protocol/registry/content compatibility for 1.26.50.4 vs Acacia 1.26.51.
3. Resolve whether the user’s strict resource-pack ban also excludes the vanilla base textures required by a renderer; do not assume permission.
4. If no candidate satisfies the constraints, report the incompatibility rather than produce a fake client.

## Release gate
An integrated client milestone requires a standalone APK, exact target-version/device test, and demonstrated rendering/input/network functionality. A companion, external overlay, native loader scaffold, or untested APK is not a client.
