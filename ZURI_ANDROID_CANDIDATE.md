# Zuri Android candidate assessment — 2026-11-24

## Summary

**Zuri is a research-only possible Android-port starting point, not a usable SparkVisuals client base today.**

- Repository: https://github.com/zuri-mc/zuri
- License: MIT (repository-level license shown by GitHub).
- Language/engine: Rust client using Bevy; separate crates include protocol/network, world, math and Xbox/auth.
- Platforms named by the README: macOS, Windows, Linux. Android is not documented as a supported target.
- Project status: explicitly “not-so-complete”; README says there is still a lot of work, and player movement is shown on a work-in-progress branch. It has no verified release APK or device test in this scan.
- The protocol crate aims at the latest Bedrock release, but exact support for 1.26.50.4 / protocol 2193 was not established.
- No source was copied, forked, built, or device-tested.

## Android portability

Bevy itself documents Android builds using the Rust Android target, cargo-ndk, and Gradle packaging. That establishes engine-level Android feasibility, **not** that Zuri's current code, rendering, authentication, network stack, or game logic works on Android.

A port would require a separate feasibility/build effort and missing client gameplay/rendering features would still need implementation. This is too large and uncertain to call a near-term SparkVisuals base.

## Other search-indexed candidate: Falcon-MC/Kestrel

A search result describes a native C++20 Bedrock client with Vulkan on Linux, D3D12 on Windows, Metal on macOS, native C++ mods, and indexed version labels including 1.26.50. However, direct GitHub page/API fetch currently returns 404. Source, exact release metadata, license, Android support, and build status could not be verified. **Do not treat it as an actionable candidate until the upstream repository is reachable and its claims can be verified.**

## Candidate comparison

| Candidate | Bedrock | Android | Integrated client | License | Decision |
|---|---|---|---|---|---|
| Cinnabar | 1.26.60 / 2225 | Experimental ARM64 APK | Yes, independent client | PolyForm Noncommercial | Closest existing client, but target mismatch and downloads vanilla RP assets; strict no-RP constraint disqualifies |
| Zuri | Latest target claimed, exact 2193 unverified | Not documented | Incomplete independent Bevy client | MIT | Research-only; Android engine support exists, but client is incomplete |
| Falcon-MC/Kestrel (search result) | Indexed release mentions 1.26.50, not directly verified | No Android docs found | Independent C++ client | Search result suggests LGPL, not verified | Inaccessible source; no adoption |
| Gato Client Mobile | README claims 1.26.50 but says protocol 2192 (conflicts with Mojang stable 2193) | Android APK/relay project | No; proxy/utility architecture | AGPL-3.0 | Excluded: separate relay, protocol claim inconsistent |

## Target-version reference

Minecraft Bedrock 1.26.50.4 is the Android client build identified for the 26.50 release family. The Mojang protocol reference lists stable 1.26.50 as protocol **2193**. Protocol compatibility does not prove hotfix compatibility or a client implementation.

## Decision and next work

No candidate satisfies all current constraints: Android, integrated standalone Bedrock client, target 1.26.50.4, no Resource Packs/Behavior Packs/Add-ons or separate mod packages, and no process injection/hooks.

Next research only:
1. Check Zuri's exact protocol schema and current branch progress against 2193.
2. Determine whether Zuri's Android build can be made to compile without pretending its unfinished gameplay is complete.
3. Continue searching for an accessible independent Bedrock client with Android support and built-in feature integration.
4. Do not adopt Cinnabar unless the strict resource-pack asset restriction is explicitly resolved; do not use proxy/loader/injection approaches.

## Sources checked

- Zuri README and license: https://github.com/zuri-mc/zuri
- Bevy official Android build guidance: https://github.com/bevyengine/bevy/blob/main/examples/README.md
- Cinnabar: https://github.com/cinnabar-mc/cinnabar
- Falcon-MC/Kestrel search-indexed page (currently 404 on direct fetch): https://github.com/Falcon-MC/Kestrel
- Gato Client Mobile: https://github.com/elgatolinux/Gato-Client-Mobile
- Mojang stable protocol changelog: https://mojang.github.io/bedrock-protocol-docs/changelog/
- Minecraft 26.50 version/build reference: https://minecraft.wiki/w/Bedrock_Edition_26.50
