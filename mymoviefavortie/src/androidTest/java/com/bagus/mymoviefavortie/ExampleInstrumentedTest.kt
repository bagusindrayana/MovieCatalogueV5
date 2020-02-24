package com.bagus.mymoviefavortie

import androidx.test.platform.app.InstrumentationRegistry


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.bagus.mymoviefavortie", appContext.packageName)
    }
}
