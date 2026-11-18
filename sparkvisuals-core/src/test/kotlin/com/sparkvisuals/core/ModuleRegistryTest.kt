package com.sparkvisuals.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ModuleRegistryTest {
    private class MemorySettings : SettingsStore {
        private val values = mutableMapOf<String, Any>()
        override fun getBoolean(key: String, default: Boolean) = values[key] as? Boolean ?: default
        override fun putBoolean(key: String, value: Boolean) { values[key] = value }
        override fun getString(key: String, default: String) = values[key] as? String ?: default
        override fun putString(key: String, value: String) { values[key] = value }
    }

    private class NoGame : GameAdapter {
        override val adapterId = "test-none"
        override fun isCompatible(gameVersion: String) = false
        override fun latestFrame(): GameFrame? = null
    }

    private class NoHud : HudRenderer {
        override fun drawText(id: String, text: String, x: Float, y: Float, scale: Float) {}
        override fun drawRect(id: String, x: Float, y: Float, width: Float, height: Float, argb: Int) {}
        override fun clear(id: String) {}
    }

    private class ProbeModule : SvModule {
        override val id = "test.probe"
        var enables = 0
        var disables = 0
        var frames = 0
        override fun onEnable(context: SvContext) { enables++ }
        override fun onDisable() { disables++ }
        override fun onGameFrame(frame: GameFrame) { frames++ }
    }

    private fun registry(settings: SettingsStore = MemorySettings()) =
        ModuleRegistry(SvContext(settings, EventBus(), NoGame(), NoHud()))

    @Test fun moduleLifecycleAndFrameDispatch() {
        val registry = registry()
        val module = ProbeModule()
        registry.register(module)
        assertFalse(registry.isEnabled(module.id))
        registry.enable(module.id)
        registry.dispatch(GameFrame(timestampMs = 1))
        assertEquals(1, module.enables)
        assertEquals(1, module.frames)
        registry.disable(module.id)
        registry.dispatch(GameFrame(timestampMs = 2))
        assertEquals(1, module.disables)
        assertEquals(1, module.frames)
    }

    @Test fun persistedEnabledStateIsRestored() {
        val settings = MemorySettings()
        settings.putBoolean("module.test.probe", true)
        val module = ProbeModule()
        registry(settings).also { it.register(module) }
        assertEquals(1, module.enables)
    }

    @Test fun duplicateIdsAreRejected() {
        val registry = registry()
        registry.register(ProbeModule())
        val error = runCatching { registry.register(ProbeModule()) }.exceptionOrNull()
        assertTrue(error is IllegalArgumentException)
    }
}
