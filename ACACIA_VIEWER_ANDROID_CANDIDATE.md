# Acacia Bedrock Viewer — Android feasibility assessment (2026-11-25)

## Summary
**Acacia is the strongest newly found independent-client architecture candidate so far, but it is not yet a SparkVisuals base.** The repository contains an interactive 3D Bedrock world viewer with player controls, rendering and UI, not merely a protocol library. Its Android build and device compatibility are not documented or verified, and its renderer depends on vanilla resource-pack assets.

## Verified upstream facts
- Repository: https://github.com/acacia-rs/acacia
- Workspace license: MIT (root Cargo workspace and LICENSE).
- Target: Minecraft Bedrock 1.26.51, wire protocol 2193. Mojang's stable 1.26.50 reference is also protocol 2193; exact 1.26.50.4 support is not verified and matching protocol numbers do not prove registry/content compatibility.
- Workspace includes `acacia-client`, `acacia-bot`, `acacia-world`, `acacia-render`, `acacia-ui`, and `acacia-viewer`.
- `acacia-viewer` uses `winit 0.30` and `wgpu 30`; it renders terrain/entities and has player movement, mining/attacking, placement/use, hotbar, inventory, chat, pause/options and debug UI. It is a real interactive networked viewer/client-like app driven through Acacia's client/bot layer.
- The project is pre-release and its APIs may change. Latest verified repository activity was 2026-11-19.
- No Android packaging instructions, Android APK/release, or Android device test were found in the inspected repository.
- Renderer setup obtains textures and UI data from Mojang/bedrock-samples vanilla resource assets and bakes look packs. Under the user's strict no-Resource-Packs rule this candidate is **not eligible unless the user later explicitly clarifies that vanilla base-game assets are acceptable**. We will not silently reinterpret that rule.
- The separate `tools/lookbake` utility is GPL-3.0-or-later per upstream design notes; it is not a dependency of the MIT client crates. Any reuse would require a full license/dependency audit.

## Comparison
| Candidate | Standalone client/rendering | Target/version | Android evidence | License/assets | Decision |
|---|---|---|---|---|---|
| Acacia viewer | Interactive 3D world viewer, UI and gameplay controls | 1.26.51 / protocol 2193; same wire number as stable 1.26.50, patch .4 unverified | No Android packaging/device evidence | MIT; vanilla look-pack assets required | Highest architecture candidate; research only |
| Cinnabar | Independent Bedrock client | 1.26.60 / 2225 | Experimental ARM64 APK | PolyForm Noncommercial; EULA-gated vanilla resource pack | Version and asset policy mismatch |
| Zuri | Incomplete Bedrock client | Exact target unverified | Android port not documented | MIT | Too incomplete |
| CaramelClient | Launcher for imported official APK + native loader | Version management, not own engine | Android APK | Apache-2.0; native mod packages | Excluded by no-addons/no-loader requirement |
| Limerence/Selaura | In-process Bedrock modification framework | Game-specific internals | Android ARM64 listed | GPL-3.0; requires modified/loaded client path | Excluded |

## What remains unverified
- Whether Acacia's current workspace can compile for `aarch64-linux-android` without source changes.
- Whether its winit event loop, Vulkan/wgpu surface, auth, networking, and input stack are usable on Android.
- App lifecycle, touch controls, keyboard/chat input, memory/thermal performance and APK packaging.
- Exact game/server compatibility with 1.26.50.4 despite shared protocol 2193.
- A texture/asset route that does not violate the no-resource-pack requirement.

## Decision
Do **not** fork or port yet. No source or assets were copied. Next research is limited to high-level Android portability and policy compatibility; no process injection/hooks, protection bypass, separate mod package, resource/behavior pack, or gameplay-cheat implementation will be developed. If the asset constraint remains literal, Acacia is disqualified regardless of Android portability.

## Sources
- https://github.com/acacia-rs/acacia
- https://github.com/acacia-rs/acacia/blob/master/crates/acacia-viewer/src/main.rs
- https://github.com/acacia-rs/acacia/blob/master/crates/acacia-viewer/Cargo.toml
- https://github.com/acacia-rs/acacia/blob/master/crates/acacia-render/README.md
- https://github.com/acacia-rs/acacia/blob/master/crates/acacia-ui/README.md
- https://github.com/acacia-rs/acacia/blob/master/docs/DESIGN.md
- https://mojang.github.io/bedrock-protocol-docs/1.26.50/
- https://github.com/cinnabar-mc/cinnabar
- https://github.com/zuri-mc/zuri
- https://github.com/ChimeraAnt-DEV/CaramelClient
- https://github.com/LimerenceMC/client
