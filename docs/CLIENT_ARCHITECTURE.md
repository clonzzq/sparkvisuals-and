# SparkVisuals client architecture

## Goal

Build a modular Android companion/client foundation for Minecraft Bedrock. Keep the existing Android app as the launcher/settings shell, while game-facing functionality is isolated behind a capability-based adapter. Do not label a feature as working until a real supported data/rendering source is implemented and tested in-game.

## Layers

1. **App shell** — navigation, theme, permissions and settings UI.
2. **Client core** — module lifecycle, configuration and feature registry. No Android or Minecraft dependency.
3. **Platform adapter** — Android-specific lifecycle and supported integration points.
4. **Game adapter** — only documented/supported Bedrock interfaces or user-imported pack APIs. Reports capabilities honestly.
5. **Presentation** — HUD widgets and visual components, only when a supported in-game rendering surface exists.

## Module lifecycle

`DISABLED -> ENABLED` only when the module's required capabilities are available. A module must fail closed when a capability is missing. Each module exposes a stable ID, display name, required capabilities and lifecycle methods.

## Initial module inventory

| Module | Required source | Initial state |
|---|---|---|
| Custom HUD | In-game rendering surface | blocked / research |
| Crosshair | In-game rendering surface | blocked / research |
| FPS | Trusted frame timing source | unavailable |
| CPS | Input event source | unavailable |
| Coordinates | Local-player position API | unavailable |
| Zoom | Camera/FOV control API | unavailable |
| Target HUD | Entity/target API | unavailable |
| Projectile prediction | Player + projectile physics state | unavailable |
| Settings / profiles | Local app storage | app-side only |

## Milestones

- M0: modular core and truthful capability model.
- M1: Android app integration and persistent module settings.
- M2: prove one supported in-game HUD rendering path on the exact target Bedrock build.
- M3: implement only features whose required game data and rendering capabilities are proven.
- M4: version compatibility tests and release packaging.

## Non-goals

No anti-cheat evasion, arbitrary process injection, game binary patching, or claims of live telemetry without a real source.
