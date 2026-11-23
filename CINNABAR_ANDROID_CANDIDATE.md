# Cinnabar Android candidate — research only (2026-11-23)

## Summary

**This is the first verified open-source project found in this scan that has both an independent Bedrock client architecture and Android APK packaging. It is a partial match, not an adopted SparkVisuals base.**

- Upstream: https://github.com/cinnabar-mc/cinnabar
- Android documentation: https://github.com/cinnabar-mc/cinnabar/blob/dev/docs/android.md
- Releases: https://github.com/cinnabar-mc/cinnabar/releases/tag/nightly and https://github.com/cinnabar-mc/cinnabar/releases/tag/v0.2.1
- Android packaging is marked experimental; target is ARM64 (arm64-v8a), Android 8+ and Vulkan.
- The latest checked nightly release (2026-11-22) includes an Android APK (93,878,361 bytes); v0.2.1 also includes an APK (101,554,375 bytes). Release assets prove packages exist, not that SparkVisuals has tested them on a device.
- Current pinned target at v0.2.1: **Minecraft Bedrock 1.26.60, protocol 2225**.
- SparkVisuals target: **1.26.50.4**. Mojang's 1.26.50 protocol reference is 2193; exact .4 patch compatibility was not separately confirmed. Cinnabar is not compatible with our target as-is. A backport would need protocol/data/version compatibility work and tests; scope is unknown.
- Architecture: Rust client with a separate Go networking/authentication core; it is a standalone client, not a launcher that injects into the official Minecraft process.
- Cinnabar's personal-mod SDK is a sandboxed WebAssembly API. It documents local-player/world observations, HUD/presentation, input and world drawing. These are separate imported mod components; the user's constraint excludes them. A future fork would have to build SparkVisuals features into the client itself rather than ship/import a separate cmod/WASM.
- First-run asset preparation requires the user to accept the Minecraft EULA and downloads Mojang's vanilla sample resource pack to prepare game assets. This is not a custom feature pack/add-on, but **if “no resource packs” means absolutely no resource-pack asset source, this candidate is disqualified**.
- License: **PolyForm Noncommercial 1.0.0**. Personal hobby/research is allowed; commercial use is not. Any redistribution of a derived client must comply with the license. This is a hard gate for any future public release.
- Cinnabar's separate official WASM module advertises 39 modules (22 HUD, 10 visual, 7 mechanic), including common HUD, waypoints, zoom and sprint toggles. Its module source/publishing repository is not publicly disclosed in the inspected listing, and it must be imported separately; no source was copied or reused.

## Decision

Research only. No Cinnabar code, APK, module, or assets have been copied, forked, installed, or device-tested as SparkVisuals. It does not meet target version 1.26.50.4 today. It is worth a feasibility study because it avoids injecting into the official Minecraft app and has an actual Android APK/client engine.

## Next feasibility checks

1. Compare Cinnabar's 1.26.60/2225 protocol and versioned data with Mojang's 1.26.50/2193 schema; estimate the backwards-port scope and identify what is version-pinned.
2. Confirm whether the Android package and renderer work on the target phone/GPU (device unknown; no device test available here).
3. Resolve whether downloading vanilla resource-pack assets is acceptable under the user's “no packs” constraint.
4. Confirm noncommercial licensing is acceptable before creating a derived branch or distributing an APK.
5. Only if these gates pass, evaluate a source fork with SparkVisuals modules compiled into the client (no separate module package).

## Sources checked

- https://github.com/cinnabar-mc/cinnabar
- https://github.com/cinnabar-mc/cinnabar/blob/dev/docs/android.md
- https://github.com/cinnabar-mc/cinnabar/blob/dev/docs/modding/src/introduction.md
- https://github.com/cinnabar-mc/cinnabar/blob/v0.2.1/assets/bedrock-target.json
- https://github.com/cinnabar-mc/cinnabar/blob/v0.2.1/LICENSE
- https://github.com/cinnabar-mc/cinnabar/releases/tag/nightly
- https://github.com/cinnabar-mc/cinnabar/releases/tag/v0.2.1
- https://cinnabar.gg/mod/
- https://mojang.github.io/bedrock-protocol-docs/1.26.50/
- https://mojang.github.io/bedrock-protocol-docs/changelog/


## Protocol backport sizing — 2026-11-23
Mojang's stable protocol changelog shows that Bedrock 1.26.60 (2225) introduced **64 changes** compared with 1.26.51 (2193): 8 new packets and 56 packet/schema changes. Examples include broad ActorEvent enum renumbering, BuildPlatform/GameType value changes, added passenger-block data in AddActor/AddPlayer, camera-field optionality, environment-attribute schema changes, and a changed sound-event structure. This confirms that moving Cinnabar back to 1.26.50.4 is not a version-string downgrade; protocol/data changes are substantial. This is a changelog-based scope signal, not a full code diff or estimate; no compatibility code was written.

Source: https://mojang.github.io/bedrock-protocol-docs/changelog/ (stable 1.26.60 and 1.26.51 sections)
