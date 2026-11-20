

## New research checkpoint — 2026-11-20

### LuaClient Mobile (new candidate, not adopted)
- Repository: https://github.com/KauanGitpy/LuaClient-Android
- The public repository describes v0.1.2 as an Android ARM64 launcher fork of LeviLauncher-Android, with native core `libLuaClient.so`, module menu, local Zoom/HUD/touch controls and a documented compatibility-safe mode.
- LICENSE checked directly: Apache-2.0. It preserves upstream notices and lists dependencies/credits.
- Its own status document says Minecraft 1.26.45.x on Android 15 enters safe mode; additional native modules are unavailable in that configuration. Its target version is not our exact 1.26.50.4, and this repository has not been built or device-tested by SparkVisuals.
- This is a launcher/client base reference, not proof that requested item-detection or trajectory features are supported. No source was copied.

### Xelo-Client and BedrockTools re-evaluated
- Xelo-Client: public Android client/launcher source, GPL-3.0. Repository README advertises launcher/config/theme/version-manager features but labels the in-game mod menu as not implemented. It is not verified for Minecraft 1.26.50.4.
- BedrockTools: MIT-licensed Android ARM64 native-mod project with many HUD/visual modules; its public release is a `.levipack` for LeviLauncher. That loader/package workflow is not the requested standalone integrated SparkVisuals client and is not adopted.
- LeviLauncher Android v1.7.1 is the latest release observed (2026-11-16); native-mod support for Minecraft 1.26.50 was added in v1.5.24. This demonstrates a loader ecosystem, not a no-loader standalone SDK.

### Decision
No verified public-source route has been found that simultaneously satisfies (1) standalone/integrated client APK, (2) no Resource/Behavior/Creator Add-ons and no separately imported mod package, (3) exact 1.26.50.4 compatibility, and (4) the requested live game-data helpers. Keep researching legal, supported SDK/runtime integration; do not present an external overlay or launcher module as the final client.
