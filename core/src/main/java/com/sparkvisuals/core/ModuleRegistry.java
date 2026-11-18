package com.sparkvisuals.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Registers modules and prevents enabling features without their real providers. */
public final class ModuleRegistry {
    private final Map<String, ClientModule> modules = new LinkedHashMap<>();
    private Set<Capability> availableCapabilities = Set.of();

    public synchronized void setAvailableCapabilities(Set<Capability> capabilities) {
        availableCapabilities = Set.copyOf(Objects.requireNonNull(capabilities));
    }

    public synchronized void register(ClientModule module) {
        Objects.requireNonNull(module, "module");
        if (module.id() == null || module.id().isBlank()) {
            throw new IllegalArgumentException("Module id must not be blank");
        }
        if (modules.putIfAbsent(module.id(), module) != null) {
            throw new IllegalArgumentException("Duplicate module id: " + module.id());
        }
    }

    public synchronized boolean canEnable(String id) {
        ClientModule module = requireModule(id);
        return availableCapabilities.containsAll(module.requiredCapabilities());
    }

    public synchronized boolean enable(String id) {
        ClientModule module = requireModule(id);
        if (module.isEnabled()) return true;
        if (!canEnable(id)) return false;
        module.onEnable();
        return true;
    }

    public synchronized void disable(String id) {
        ClientModule module = requireModule(id);
        if (module.isEnabled()) module.onDisable();
    }

    public synchronized ClientModule get(String id) {
        return requireModule(id);
    }

    public synchronized Collection<ClientModule> all() {
        return Collections.unmodifiableList(java.util.List.copyOf(modules.values()));
    }

    private ClientModule requireModule(String id) {
        ClientModule module = modules.get(id);
        if (module == null) throw new IllegalArgumentException("Unknown module: " + id);
        return module;
    }
}
