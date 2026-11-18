package com.sparkvisuals.core;

import java.util.Set;

/** Platform-neutral contract for a SparkVisuals feature module. */
public interface ClientModule {
    String id();
    String displayName();
    Set<Capability> requiredCapabilities();

    /** Called after required capabilities have been verified. */
    void onEnable();

    /** Called before the module is disabled or the client shuts down. */
    void onDisable();

    boolean isEnabled();
}
