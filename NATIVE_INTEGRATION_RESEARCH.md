# Native Bedrock integration research (SparkVisuals)

Research checked on 2026-11-19.

## Candidate reference
- BedrockTools: https://github.com/QYCottage/BedrockTools
- Public project description: native C++20 mod for Bedrock Android, designed for LeviLauncher/Preloader, Android 9+, arm64-v8a.
- README describes HUD modules including FPS, Ping, player coordinates, CPS, and mod menu/config integration.
- Project release observed: v1.5.12 (2026-11-07). This is not confirmation of compatibility with the user's target build.
- License is GPL-3.0. Review license obligations before copying or adapting source.

## SparkVisuals target
- User's target: Minecraft Bedrock Android 1.26.50.4, arm64 assumed but must be device-confirmed.
- Current SparkVisuals repo has only the cloud-build workflow and a 0.3.1 UI prototype archive; no native source integration yet.
- No compatibility test was performed on the user's phone. No native module has been built or installed.

## Next milestones (non-invasive / supported development)
1. Confirm device ABI and exact Minecraft build from device.
2. Review upstream compatibility documentation/release notes for target build and loader.
3. Establish whether an official/supported mod development interface exists for the desired telemetry.
4. If compatibility and permissions are established, prototype a minimal native HUD in a local test environment for FPS and ping; do not fabricate telemetry or claim it works until tested.
5. Build a standalone Black/Red UI preview and settings persistence separately from any Minecraft integration.

## Safety and compatibility
Do not attempt to evade anti-cheat, defeat platform protections, or modify a game installation without authorization. Public-server rules and Minecraft terms may restrict third-party client modifications. FPS/ping HUD implementation remains unverified.
