# SparkVisuals standalone-client roadmap

## Status
No integrated client APK exists. The current Android APK is a companion prototype only. The former native-loader mod scaffold and its build/test workflow were removed on 2026-11-25 to align the repository with the no-packs/no-addons/no-process-modification requirements.

## Research gates
- [x] Confirm companion-only behavior and state limitations in README.
- [x] Remove excluded native-mod package source, manifest, packaging script, and native test workflow.
- [x] Research Acacia: full viewer architecture, MIT, protocol 2193; Android packaging unverified; resource-pack assets required.
- [x] Reconfirm Cinnabar (experimental Android, newer protocol, PolyForm NC, vanilla assets) and Zuri (incomplete, Android unverified).
- [ ] Review Android portability of Acacia viewer's winit/wgpu stack without porting code.
- [ ] Verify exact 1.26.50.4 protocol/content compatibility (same protocol number alone is insufficient).
- [ ] Determine whether vanilla base assets can be obtained without violating the user's literal Resource Packs ban; do not proceed under an assumption.

## If a candidate ever passes
- Establish source/license and dependency audit.
- Confirm Android target and app lifecycle.
- Build a standalone APK (no launcher overlay or separately imported mod).
- Test exact Minecraft target/device and document every verified feature.
- Do not add gameplay-advantage/automation features or unsupported process modification.
