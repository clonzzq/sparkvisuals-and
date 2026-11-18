# SparkVisuals Core (foundation)

This directory contains platform-independent Java interfaces for module lifecycle and capability reporting. It is deliberately not wired into the Android APK yet: the existing archive is a Java Android project and should be integrated only after its Gradle structure is reviewed.

Compile the source files directly with Java 17:

```sh
javac -d out $(find src/main/java -name '*.java')
```

The core does not access Minecraft memory, Android internals, or game state. A platform/game adapter must explicitly provide capabilities before a module can enable.
