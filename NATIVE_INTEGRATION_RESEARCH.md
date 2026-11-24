

## 2026-11-22 — additional standalone-client and license scan
- **Kestrel (MIT)** and **Starling (MIT)** are native Android game clients/mod SDK projects for Minecraft Java 26.3. They are useful architectural references for independent clients with controlled mod APIs, but neither implements Bedrock 1.26.50.4; no source was reused.
- **Alex (MPL-2.0)** is a cross-edition C# project, but its maintainer marks it unmaintained, lists Bedrock 1.19.10 and Android as planned. Not suitable.
- **Selaura (GPL-3.0)** lists Android 9+ ARM64 support and an Android launcher, but it is a Bedrock modding framework/modified client, not an independent supported standalone client. Excluded by constraints; no source copied.
- **M9CHKO/bedrock-protocol**: protocol/network library, version data through 1.26.20 only; README requests adding a project license, so code reuse is not authorized by a verified license. Protocol 2193 for stable 1.26.50 is confirmed by Mojang's reference. A protocol library alone does not provide rendering/game UI or integration into the official app.
- Local host test verification reconstructed from retrieved repository source: CMake build succeeded, CTest 2/2 passed (GCC 14.2). This is not an official CI run and does not verify Android or native-loader integration. Companion APK run 44899111488 succeeded; artifact hash/size recorded in the research log.
- No candidate meets all user constraints. No client-integration code was added.

### Sources
- https://github.com/NepThunder/Kestrel
- https://github.com/starling-client/starling
- https://github.com/ConcreteMC/Alex
- https://github.com/selauraclient/selaura
- https://github.com/M9CHKO/bedrock-protocol
- https://mojang.github.io/bedrock-protocol-docs/1.26.50/
- https://github.com/clonzzq/sparkvisuals-and/actions/runs/44899111488


## 2026-11-23 — Cinnabar Android standalone client candidate
- Verified Cinnabar: Rust independent Bedrock client plus Go core; Android arm64 APK packaging is explicitly experimental and Android 8+ / Vulkan. Both nightly and v0.2.1 release metadata contain Android APK assets; no SparkVisuals device test was done.
- v0.2.1 target manifest: 1.26.60 / wire protocol 2225. Mojang's 1.26.50 protocol reference is 2193 (the exact 1.26.50.4 hotfix was not separately confirmed). Not compatible with SparkVisuals target as-is; backport work is unestimated.
- PolyForm Noncommercial 1.0.0; personal hobby/research allowed, commercial use prohibited. First-run downloads official vanilla resource-pack assets under EULA acceptance. Literal no-resource-pack policy would exclude it.
- Personal-mod API is sandboxed WASM and can observe player/world, render HUD/world and respond to input. Separate imported mod files are excluded; a derived client would need built-in features. Official Cinnabar Mod advertises 39 modules but is a separate imported WASM and its source was not reused.
- Status: promising standalone architecture partial match; research only. No code or assets copied, no fork, no APK/device test.
- Full report: [CINNABAR_ANDROID_CANDIDATE.md](CINNABAR_ANDROID_CANDIDATE.md).

Sources: https://github.com/cinnabar-mc/cinnabar ; https://github.com/cinnabar-mc/cinnabar/blob/dev/docs/android.md ; https://github.com/cinnabar-mc/cinnabar/blob/v0.2.1/assets/bedrock-target.json ; https://github.com/cinnabar-mc/cinnabar/blob/v0.2.1/LICENSE ; https://cinnabar.gg/mod/ ; https://mojang.github.io/bedrock-protocol-docs/1.26.50/ ; https://mojang.github.io/bedrock-protocol-docs/changelog/

- Protocol backport sizing check: Mojang changelog lists 64 stable protocol changes in 1.26.60 vs 1.26.51/1.26.50 family protocol 2193 (8 added packets, 56 changed). Changes include enums, fields, and packet schemas; the port is substantial, not a constant edit. No code ported. Source: https://mojang.github.io/bedrock-protocol-docs/changelog/
## 2026-11-23 — Gato Mobile and BedrockParty verification
- Gato-Client-Mobile README describes an Android utility APK plus a local JVM packet relay/MITM; the official Minecraft game is still launched separately and pointed through the relay. This is not an integrated client/HUD architecture, so it does not satisfy the no-companion/no-overlay target.
- Version claim mismatch: Gato README says 1.26.50 uses protocol 2192, but Mojang's stable protocol reference/changelog reports 2193 for 1.26.50; 2192 appears in preview protocol metadata. Treat compatibility as unverified and the README claim as incorrect/inconsistent until upstream demonstrates otherwise. Exact 1.26.50.4 patch metadata was not independently located.
- License AGPL-3.0 with GPL-derived portions; no source reuse. The repository scan did not verify a published release APK, CI status, or device runtime.
- BedrockParty reports physical Android clients joining BDS at stable 1.26.50/protocol 2193; it is an interoperability bridge, not an in-game mod/client UI.
- No new viable implementation path within the user's no-pack/no-addon/no-injection constraints; no code changes.
Sources: https://github.com/elgatolinux/Gato-Client-Mobile ; https://mojang.github.io/bedrock-protocol-docs/changelog/ ; https://mojang.github.io/bedrock-protocol-docs/1.26.50/ ; https://github.com/dockopslab/bedrockparty-lan-bridge


## 2026-11-24 — BedrockTools (native in-process mod candidate) assessed, not adopted
- QYCottage/BedrockTools is a genuine Android ARM64 C++20 LeviLauncher/Preloader native mod with 51 modules including customizable HUD (Armor/Potion/Coords/Ping/Compass/Speed/etc.), Zoom, camera/visual modules, module settings and launcher menu integration. Its v1.5.6 target manifest explicitly says `1.26.5X.X`; v1.5.12 latest targets `1.26.6X.X`. Exact target build .50.4 and device runtime have not been verified by this project.
- This is a meaningful architectural match to the desired *in-game* feature surface, but it requires native code loaded into Minecraft's process by LeviLauncher. The user expressly excludes injection/hooks/process modification, so this path is not usable under the current constraints and we will not develop or instruct it.
- Licensing discrepancy: repository LICENSE + GitHub metadata show MIT; README at v1.5.6 says GPL-3.0. Source reuse is paused until upstream resolves which license applies and dependencies are audited.
- No code/assets reused; no APK/CI or phone test; no change to SparkVisuals native game integration.
Sources: https://github.com/QYCottage/BedrockTools/releases/tag/v1.5.6 ; https://github.com/QYCottage/BedrockTools/releases/tag/v1.5.12 ; https://github.com/QYCottage/BedrockTools/blob/v1.5.6/levimod.json ; https://github.com/QYCottage/BedrockTools/blob/v1.5.6/README.md ; https://github.com/QYCottage/BedrockTools/blob/main/LICENSE ; https://levimc.org/LeviLauncher/concepts/mod-loading
