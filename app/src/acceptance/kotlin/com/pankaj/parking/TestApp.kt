package com.pankaj.parking

import kotlin.concurrent.thread

class TestApp(reader: MockReader, writer: MockWriter) {
    private val app = App(reader::read, writer::write)
    private var appThread: Thread? = null

    fun start() {
        appThread = thread { app.start() }
    }

    fun stop() {
        appThread?.stop()
    }
}
