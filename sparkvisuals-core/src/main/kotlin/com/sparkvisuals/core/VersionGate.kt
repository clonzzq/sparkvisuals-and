package com.sparkvisuals.core

data class Compatibility(
    val supported: Boolean,
    val reason: String
)

/** Fail-closed version gate: unknown versions are never treated as supported. */
class VersionGate(private val supportedVersions: Set<String>) {
    fun check(version: String, adapter: GameAdapter): Compatibility {
        if (version !in supportedVersions) {
            return Compatibility(false, "Minecraft version $version has not been validated")
        }
        if (!adapter.isCompatible(version)) {
            return Compatibility(false, "Adapter ${adapter.adapterId} rejected $version")
        }
        return Compatibility(true, "Version and adapter match")
    }
}
