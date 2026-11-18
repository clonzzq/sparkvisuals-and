package com.sparkvisuals.core

/** Data exposed only by an explicitly supported integration. Null means unavailable. */
data class GameFrame(
    val connected: Boolean = false,
    val fps: Int? = null,
    val pingMs: Int? = null,
    val playerX: Double? = null,
    val playerY: Double? = null,
    val playerZ: Double? = null,
    val dimension: String? = null,
    val timestampMs: Long
)

interface GameAdapter {
    val adapterId: String
    fun isCompatible(gameVersion: String): Boolean
    fun latestFrame(): GameFrame?
}

interface SettingsStore {
    fun getBoolean(key: String, default: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getString(key: String, default: String): String
    fun putString(key: String, value: String)
}

interface HudRenderer {
    fun drawText(id: String, text: String, x: Float, y: Float, scale: Float = 1f)
    fun drawRect(id: String, x: Float, y: Float, width: Float, height: Float, argb: Int)
    fun clear(id: String)
}

fun interface Subscription { fun cancel() }

class EventBus {
    private val listeners = mutableMapOf<String, MutableSet<(Any) -> Unit>>()

    @Synchronized
    fun subscribe(event: String, listener: (Any) -> Unit): Subscription {
        val set = listeners.getOrPut(event) { linkedSetOf() }
        set += listener
        return Subscription { synchronized(this) { set -= listener } }
    }

    @Synchronized
    fun publish(event: String, payload: Any) {
        listeners[event]?.toList()?.forEach { it(payload) }
    }
}
