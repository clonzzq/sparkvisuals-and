# Flarial / Atlas research notes

Research date: 2026-11-18.

## Flarial

The public `flarialmc/dll-oss` repository describes a Minecraft Bedrock client, but its README lists Windows 10/11 and the source targets Windows UWP/GDK. The public repository is therefore an architecture reference, not an Android drop-in dependency. Its README also states that key portions remain private and that signatures/offsets can lag current Minecraft versions.

Useful architectural ideas visible in the public code:
- explicit client initialization and version support checks;
- separate Hook, Module, Event, Settings, GUI and Script managers;
- a local player state abstraction;
- modules initialized only after core systems are ready.

We should reproduce the separation of concerns, not copy Windows injection, memory hooks, offsets, or proprietary implementation.

## Atlas

The public AtlasClientMC organization exposes translations, an old website repository, and Dobby (a multi-platform hook framework fork). It does not expose the complete Atlas client implementation. Dobby alone is not a Minecraft integration layer and does not provide game APIs, offsets, or a renderer.

## Android implications

SparkVisuals targets Android Bedrock. Windows DLL hooks and x64 offsets cannot be reused as-is for Android ARM64. Any native integration would require a legitimate, version-specific integration path and explicit compatibility testing. No anti-cheat bypass, process tampering, or modified proprietary game binary is part of this project.

## Sources

- https://github.com/flarialmc/dll-oss
- https://github.com/flarialmc/dll-oss/blob/main/src/Client/Client.cpp
- https://github.com/AtlasClientMC
- https://github.com/AtlasClientMC/Dobby
