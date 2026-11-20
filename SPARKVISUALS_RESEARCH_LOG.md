# SparkVisuals research log

## 2026-11-20
- **Verified version family:** LeviLauncher Android v1.5.24 release notes explicitly say Minecraft 1.26.50 support; not exact patch/device verification.
- **API boundary:** Official native mod path is Preloader SDK running native code in the game process. Public docs describe UI/config/input/draw APIs but no public game-state API; state-dependent modules rely on internals. This route does not meet the no-injection/hooks constraint.
- **Official Creator API:** Minecraft 1.26.50 Creator APIs remain pack/creator-content APIs; not a standalone Android client API. Packs/add-ons are out of scope.
- **Android overlay alternative:** system application overlays are external to Minecraft, permission-based, and Android 12+ can hide/block interactions; rejected as not a full in-game client.
- **CI:** waypoint-core tests succeeded (run 44514320765). Native prototype and Android debug APK CI builds succeeded (runs 44525792538 / 44525792379). These are build/test evidence only, not runtime integration.
- **Outcome:** no safe, verified technical path found that meets all constraints. No source changes this run; documentation-only research update.

### Source links
- LeviLauncher release notes: https://github.com/LiteLDev/LeviLauncher-Android/releases
- Native mod quick start: https://levimc.org/LeviLauncher/android/guide/developer
- LeviLauncher Android FAQ/API limits: https://levimc.org/LeviLauncher/faq/play/android/usage/
- Bedrock 1.26.50 Creator update: https://learn.microsoft.com/en-us/minecraft/creator/documents/update1.26.50
- Android overlay restrictions: https://developer.android.com/about/versions/12/features and https://developer.android.com/about/versions/12/behavior-changes-all
- SparkVisuals CI: https://github.com/clonzzq/sparkvisuals-and/actions/runs/44514320765, https://github.com/clonzzq/sparkvisuals-and/actions/runs/44525792538, https://github.com/clonzzq/sparkvisuals-and/actions/runs/44525792379
