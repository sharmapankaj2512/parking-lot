package com.pankaj.parking

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class FeatureSetup {
    val reader = MockReader()
    val writer = MockWriter()
    val app = TestApp(reader, writer)

    @BeforeEach
    fun beforeEach() {
        app.start()
    }

    @AfterEach
    fun afterEach() {
        app.stop()
    }
}