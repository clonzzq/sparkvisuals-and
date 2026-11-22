

## 2026-11-22 — protocol client and alternate Android client review
- **M9CHKO/bedrock-protocol:** public C++20 network/protocol client library with RakNet, Xbox auth, packet codecs, relay and Android relay-app pieces. Its current bundled version list ends at Bedrock 1.26.20 (protocol 975). It does not list 1.26.50.4; Mojang protocol reference lists the 1.26.50-beta.0 family at protocol 2193. Compatibility for the target is not established.
- The library is not a game renderer or supported bridge into the official Bedrock app. A protocol client/relay would be an independent network endpoint, not an integrated HUD. It cannot satisfy the requested full in-game client without a separate renderer/client implementation and game integration.
- **AllayMC/Protocol** is also a protocol library, not an Android Bedrock rendering client.
- **Starling and Kestrel** are current Android Minecraft Java Edition 26.3 projects, so they do not target Bedrock 1.26.50.4 and are not a base for this client.
- No source reuse, code, APK, or CI build added. Research-only change; current constraints still block a verified standalone integrated Bedrock client.

### Sources
- https://github.com/M9CHKO/bedrock-protocol
- https://github.com/M9CHKO/bedrock-protocol/blob/main/documentation/VERSIONS.md
- https://mojang.github.io/bedrock-protocol-docs/1.26.50/
- https://github.com/AllayMC/Protocol
- https://github.com/starling-client/starling
- https://github.com/NepThunder/Kestrel
