package com.sparkvisuals.core

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VersionGateTest {
    private class Adapter(private val accepted: Boolean) : GameAdapter {
        override val adapterId = "test-adapter"
        override fun isCompatible(gameVersion: String) = accepted
        override fun latestFrame(): GameFrame? = null
    }

    @Test fun unknownVersionFailsClosed() {
        val result = VersionGate(setOf("1.26.50.4")).check("1.26.51", Adapter(true))
        assertFalse(result.supported)
    }

    @Test fun adapterMustAlsoAcceptVersion() {
        val result = VersionGate(setOf("1.26.50.4")).check("1.26.50.4", Adapter(false))
        assertFalse(result.supported)
    }

    @Test fun supportedVersionAndAdapterPass() {
        val result = VersionGate(setOf("1.26.50.4")).check("1.26.50.4", Adapter(true))
        assertTrue(result.supported)
    }
}
