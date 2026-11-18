# SparkVisuals Core Foundation

This is the first modular core scaffold for SparkVisuals, inspired by the separation of concerns used in projects such as Flarial.

## Included
- `SvModule` lifecycle and `ModuleRegistry`
- `GameAdapter` boundary for a supported game integration
- `GameFrame` telemetry model (unavailable values stay null; no fake FPS/ping)
- `SettingsStore` and `HudRenderer` interfaces
- lightweight `EventBus`
- fail-closed `VersionGate`

## Important status
This is **architecture code, not a functioning Minecraft Bedrock client yet**. No Minecraft process hooks, native injection, memory reads, anti-cheat evasion, or proprietary game modifications are included. The current repository ZIP/APK is not changed by this scaffold.

For Android Bedrock, a real in-game adapter and renderer require a supported integration surface. The adapter must remain unavailable until that surface is implemented and tested against the exact game build. The target version currently tracked by the project is 1.26.50.4; it is not marked compatible merely because it is the target.

## Next engineering steps
1. Add unit tests for registry lifecycle, settings persistence, and version rejection.
2. Connect the core to the Android app's existing settings and UI.
3. Identify a supported Bedrock integration path for in-game rendering and telemetry.
4. Implement HUD modules only after the adapter can provide real data.

## Package
`com.sparkvisuals.core`
