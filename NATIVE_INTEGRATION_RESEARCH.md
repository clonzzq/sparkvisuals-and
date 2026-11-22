

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
