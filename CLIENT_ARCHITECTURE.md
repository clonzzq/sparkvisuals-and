# SparkVisuals Client — direction correction

## Product definition
SparkVisuals is intended to be a standalone Android client application (its own APK, name, branding, launcher UI, settings, profiles, and Play/start flow), not a mod installed through a separate launcher app.

Minecraft Bedrock's proprietary engine remains the licensed game dependency. We do not recreate, redistribute, or bypass Minecraft licensing.

## Architecture options to investigate
1. Build SparkVisuals as a standalone Android app that integrates a legitimate game-launch/runtime approach inside the app, subject to legal/licensing constraints and Android compatibility.
2. Reuse or fork open-source Android launcher/client code only where its license permits, retaining required notices and GPL obligations if applicable. Forking a launcher codebase does not make the Minecraft engine ours.
3. Do not make a separate LeviLauncher installation a required user-facing dependency. If reusable launcher components are needed, integrate/adapt them within SparkVisuals after license and technical review.
4. In-game UI/mod features must use supported, legitimate integration methods; do not implement anti-cheat evasion, game-protection bypass, hidden-player/object detection, or competitive gameplay automation.

## MVP
- SparkVisuals Android APK and branding
- Black/charcoal + crimson design system
- Home screen, game version/import flow, settings, profiles, launch flow
- Legal game ownership / APK import workflow
- UI customization and noncompetitive HUD metrics where technically supported
- User-created coordinate waypoints

## Honest status
This repository currently contains an earlier native-mod prototype/scaffold, not the standalone client APK. It is not built or tested. This document supersedes that prototype as the product direction. Technical feasibility and compatible licensed launch/runtime integration must be validated before promising an APK.
