# SparkVisuals — client architecture (updated 2026-11-21)

## Goal and constraints
A real integrated Android client for Minecraft Bedrock 1.26.50.4. Companion APK is not the deliverable. User excludes Resource Packs, Behavior Packs, Creator Add-ons, separately imported native-mod packages, and code injection/hooks.

## Routes examined
- **LeviLauncher Android / Preloader:** https://github.com/LiteLDev/LeviLauncher-Android and https://levimc.org/LeviLauncher/android/guide/developer. Release v1.5.24 added Minecraft 1.26.50 family support; latest observed release v1.7.1 (2026-11-16). Exact 1.26.50.4/device compatibility is not verified. Native modules load through LeviLauncher/Preloader; not a standalone client.
- **Public cosmetic Mod Menu API:** https://levimc.org/LeviLamina/developer_guides/android/api/mod_menu/ documents module config/toggles, floating buttons, `submitDrawCommands()`, font/image registration, HUD-editor elements, and HUD surface size. This establishes a supported API surface for cosmetic draw commands, not game telemetry. Using it still requires a native module loaded through the launcher and is excluded by the user's no-separate-module/no-injection constraints.
- **BedrockTools:** https://github.com/QYCottage/BedrockTools. MIT; native C++ Android mod for LeviLauncher/Preloader with wrappers, signatures/offsets and event API. Requires native-module loading and game-internal version-specific interfaces; not adopted.
- **LuaClient Mobile:** https://github.com/KauanGitpy/LuaClient-Android. Public v0.1.2 launcher fork, Apache-2.0; exact target compatibility unverified.
- **Xelo-Client:** https://github.com/Xelo-Client/Xelo-Client. GPL-3.0; launcher-oriented, in-game menu marked not implemented; exact target unverified.
- **Flarial Android:** https://flarial.xyz/ and https://play.google.com/store/apps/details?id=xyz.flarial.client. Android app listing is a possible existing client for user-side evaluation; source/license and exact patch/device compatibility not established, so not a SparkVisuals base.

## API and technical limits
- Official Minecraft Creator APIs are content/pack-based and excluded.
- LeviLauncher Mod Menu public API can draw a cosmetic overlay, but does not provide a verified public live-game-state API. Native mod loading is not the standalone/no-separate-module route requested.
- BedrockTools and other native mods may read game data through version-specific hooks/signatures/offsets; those mechanisms are excluded from implementation.
- Android system overlays are external to Minecraft and are not an acceptable substitute.

## Current status
- Companion APK builds; its overlay is outside Minecraft and reads only manually saved local coordinates/markers.
- Native core (settings/waypoints) and host tests exist; source has a cosmetic Mod Menu draw-command prototype. Native SDK build and in-game runtime are not verified.
- No native mod package is generated or accepted as the deliverable. No SparkVisuals integrated client APK exists.

## Safe work scope
Standalone UI/config, personal waypoint storage and harmless UI only. No injection, hooks, signature scans, patches, protection bypass, game-state item/entity detection, projectile prediction, or gameplay automation.

## Release gate
Never label a shell, companion, native scaffold, or package as the client. A client milestone requires integrated launch, exact game version/device test, and demonstrated functionality.


## Cinnabar Android candidate — research only (2026-11-23)
- Cinnabar is a newly verified independent Bedrock client with experimental Android arm64-v8a APK packaging and nightly/beta APK releases. It is a standalone Rust client with a Go network/auth core; no injection into the official Minecraft process.
- The inspected v0.2.1 target is 1.26.60 / protocol 2225; SparkVisuals target is 1.26.50.4 / 1.26.50 family protocol 2193. No backwards compatibility is established.
- License is PolyForm Noncommercial 1.0.0; personal hobby use is permitted, commercial use is not. First-run preparation downloads Mojang's vanilla sample resource pack after EULA acceptance; a literal ban on any resource-pack source would exclude this candidate.
- Its WASM mod SDK exposes local player/world observations and HUD/world drawing, but imported modules are separate packages and excluded. A future fork would need built-in SparkVisuals modules in client source. No code was copied or forked.
- Full assessment: [CINNABAR_ANDROID_CANDIDATE.md](CINNABAR_ANDROID_CANDIDATE.md). Research only; no exact-version/device proof.
