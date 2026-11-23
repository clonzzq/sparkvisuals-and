
- [x] Additional standalone-client/license scan: Kestrel and Starling are Java 26.3 Android clients; Alex is unmaintained and lists Android as planned; Selaura is a GPL-3 Bedrock mod framework/launcher route excluded by constraints; M9CHKO protocol library lacks a verified license and target-version coverage. No candidate satisfies constraints (2026-11-22).
- [x] Reconstructed host CMake/CTest from retrieved native-core source; 2/2 tests pass locally. This does not verify Android/Preloader integration or the official workflow (2026-11-22).

- [x] 2026-11-23: Verified Cinnabar independent Bedrock client, experimental Android arm64 APK releases and built-in mod API architecture. Partial match only: target 1.26.60/protocol 2225 vs our 1.26.50 family/2193, PolyForm Noncommercial, vanilla resource-pack asset preparation, no device verification. No source copied/adopted.
- [x] 2026-11-23: Changelog scope check shows 64 stable protocol changes between 1.26.51 (2193) and 1.26.60 (2225): 8 added packets and 56 changed. Backport is substantial; no compatibility code written.
- [ ] Next: inspect Cinnabar code-level version pinning and estimate protocol/data backport; Android GPU/device test; clarify asset-pack policy and license; only then decide whether to fork and embed SparkVisuals features into client source (no separate WASM module).
