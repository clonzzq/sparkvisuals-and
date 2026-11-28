# Protocol 2193 tooling and validation research — 2026-11-28

## New findings

### bedrock-mc/protocolgen — generated Rust packet codecs
- Current generated release `1.26.51` covers the Bedrock 1.26.50/1.26.51 stable family at protocol **2193**; a separate `1.26.50` directory is preview-derived protocol 2187, so use the release-family record, not the preview directory.
- Generates Rust and Go packet codecs from reconciled manifests. The manifest uses source locks and fingerprinted corrections; CI verification can detect generated drift.
- The generator and its authored/generated implementation contributions are MIT. Imported documentation, schemas, game-data and source materials retain separate terms; NOTICE/data notices must be audited before reuse. This is a protocol reference, not a game client, renderer, Android app, or official telemetry API.
- Candidate for packet-schema/reference work only; no code copied or dependency added.

### EndstoneMC/bedrock-protocol — C++ packet library
- MIT; C++23 plus Python/Java interfaces.
- Its README demonstrates protocol 2193 with the pinned v2193 codec.
- It provides packet encode/decode and protocol selection, not RakNet session/authentication, game loop, renderer, touch controls or Android packaging.
- Could be evaluated as an Android NDK/JNI packet-codec dependency only after C++23/NDK build, dependency/license and API surface audits. No port/build performed.

### BedrockParty — exact-family test reference, not a client
- MIT LAN bridge, source-only releases.
- Upstream README documents validated Android/RakNet ↔ BDS play on Bedrock 1.26.50, protocol 2193, with gophertunnel 1.62.0.
- This is useful evidence that real Android clients and BDS 1.26.50 work on protocol 2193 and a potential future test-server fixture. It is not a standalone client, does not render a world, and does not provide in-game HUD APIs. No deployment or test against it performed.

### Version caveat
- SparkVisuals target is the exact Android patch **1.26.50.4**. Protocol 2193 is a family-level match, but the reviewed sources do not prove exact .4 compatibility. A real exact-version server/device test is still required.

## Code change completed
- Hardened RakNet unconnected-ping handling: the pong's echoed timestamp must match the request timestamp. A mismatch now returns `TransportError::PingTimestampMismatch` instead of being accepted.
- Added a loopback mock test that deliberately sends a mismatched timestamp and checks rejection.
- GitHub Actions run **#20** on commit `68243cc` passed **19/19 host tests**. Tests use local mock UDP peers and packet fixtures only; no real Bedrock server, Android build, or phone validation.
- No APK built. No packs/add-ons/mod packages, process hooks, or gameplay-automation code added.

## Decision
- Protocol-generation tools may be useful for auditing packet definitions, but they do not solve the renderer or asset constraint.
- The no-resource-pack requirement remains a hard gate. A clean-room renderer using original assets is a separate, large project and is not started in this run.
- No external code/assets copied; no dependencies added.

## Sources
- https://github.com/bedrock-mc/protocolgen
- https://github.com/EndstoneMC/bedrock-protocol
- https://github.com/dockopslab/bedrockparty-lan-bridge
- https://mojang.github.io/bedrock-protocol-docs/1.26.50/
- https://github.com/clonzzq/sparkvisuals-and/actions/runs/46138837835
