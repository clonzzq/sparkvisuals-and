# Native/in-process integration research — excluded paths

**Current decision (2026-11-25):** no in-process native-mod route will be implemented. The earlier `SparkVisualsNative/` scaffold, .levipack packaging script, and host-test workflow were deleted. They were a static status-panel prototype using a native loader, not a standalone client.

## Previously examined paths
- LeviLauncher/Preloader + BedrockTools and similar native mods: require native code loaded into Minecraft's process. Excluded by the user’s constraints; no implementation guidance or source reuse.
- Creator APIs: pack/add-on based, excluded.
- External Android overlay: outside Minecraft, manual coordinates only, not a substitute for integrated HUD.
- Cinnabar and Zuri: independent-client research remains in their separate assessment docs; neither meets all constraints.
- **Acacia viewer:** full Rust/wgpu/winit interactive viewer, MIT, protocol 2193 target, but Android packaging unverified and vanilla resource-pack assets are required. Strict pack ban disqualifies unless clarified. See [assessment](ACACIA_VIEWER_ANDROID_CANDIDATE.md).

## Historical verification (not current code)
- Host CMake/CTest 2/2 passed in an earlier reconstructed test run. That test code was subsequently removed along with the excluded native-mod scaffold. This is not an Android SDK build, in-game test, or current CI result.
- Android companion CI run #13 succeeded on 2026-11-21; it verifies only the companion APK.

## Conclusion
No implementation of in-process hooks, signatures, offsets, patches, process injection, anti-cheat/protection bypass, or gameplay-advantage modules will be developed. Research may compare independent client architectures and licenses at a high level. No integrated client APK exists.
