# SparkVisuals Native

New Android Bedrock native-mod project scaffold. This intentionally does not use the legacy 0.3.1 APK shell.

## Target
- Minecraft Bedrock Android 1.26.50.4 (compatibility is not yet verified)
- Android ARM64
- Native-mod loader integration: research/prototype stage
- Branding: charcoal/black with crimson accents

## Initial module scope
- FPS and CPS HUD (telemetry source must be connected)
- Coordinates HUD (game API source must be connected)
- Potion/effect HUD and cooldown display (game data source must be connected)
- Armor HUD (game data source must be connected)
- Crosshair appearance and zoom settings (only through supported interfaces)
- User-created waypoint markers with labels, coordinates, distance, color, persistence
- HUD layout editor/settings

Gameplay automation and competitive advantage modules are not included in this scaffold.


## Current status
This is project scaffolding only: no loader integration, no runtime hooks, no game telemetry, and no installable mod yet. Next step is verify the loader SDK/template and supported APIs for the exact Bedrock version before implementing runtime UI.