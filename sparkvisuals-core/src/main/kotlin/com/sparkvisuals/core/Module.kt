package com.sparkvisuals.core

/** Lifecycle contract for a SparkVisuals feature. Modules must not assume a live game connection. */
interface SvModule {
    val id: String
    val displayName: String
    val defaultEnabled: Boolean get() = false
    fun onEnable(context: SvContext) {}
    fun onDisable() {}
    fun onGameFrame(frame: GameFrame) {}
}

data class SvContext(
    val settings: SettingsStore,
    val events: EventBus,
    val game: GameAdapter,
    val hud: HudRenderer
)

class ModuleRegistry(private val context: SvContext) {
    private val modules = linkedMapOf<String, SvModule>()
    private val enabled = mutableSetOf<String>()

    fun register(module: SvModule) {
        require(module.id.matches(Regex("[a-z0-9_.-]+"))) { "Invalid module id: ${module.id}" }
        require(module.id !in modules) { "Duplicate module id: ${module.id}" }
        modules[module.id] = module
        if (context.settings.getBoolean("module.${module.id}", module.defaultEnabled)) enable(module.id)
    }

    fun all(): List<SvModule> = modules.values.toList()
    fun isEnabled(id: String): Boolean = id in enabled

    fun enable(id: String) {
        val module = requireNotNull(modules[id]) { "Unknown module: $id" }
        if (enabled.add(id)) {
            context.settings.putBoolean("module.$id", true)
            module.onEnable(context)
        }
    }

    fun disable(id: String) {
        val module = requireNotNull(modules[id]) { "Unknown module: $id" }
        if (enabled.remove(id)) {
            module.onDisable()
            context.settings.putBoolean("module.$id", false)
        }
    }

    fun dispatch(frame: GameFrame) {
        modules.values.filter { it.id in enabled }.forEach { it.onGameFrame(frame) }
    }
}
