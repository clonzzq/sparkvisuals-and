# SparkVisuals Android companion (0.2.0)

A standalone Android companion app for Minecraft Bedrock. This repository is actively used for the prototype; APKs are built by GitHub Actions.

## Current functionality
- Dark crimson dashboard and settings.
- Launch the official installed Minecraft Bedrock app.
- HUD preference toggles saved on device (configuration flags only).
- Waypoints: add/edit/delete, choose color, toggle enabled, store X/Y/Z locally.
- Enter a current position manually and calculate horizontal X/Z distance to saved waypoints.
- Android floating overlay: draggable panel shows manually entered coordinates and distance to the nearest enabled waypoint.
- Android 13 (API 33)+.

## Build
Open [GitHub Actions](https://github.com/clonzzq/sparkvisuals-and/actions), run or wait for **Build SparkVisuals Android prototype**, then download the APK artifact. The workflow runs `gradle clean assembleDebug` in `SparkVisualsApp` and verifies the APK exists before uploading.

## Important integration note
This is an external companion and system overlay, not an integrated in-game client. It does not read Minecraft memory, game state, or live coordinates. HUD toggles are saved preferences and are not rendered inside Minecraft. No injection, hooking, patching, or game-process modification is used.

Target game version noted in research: Bedrock Android 1.26.50.4; exact compatibility/integration is not claimed.
