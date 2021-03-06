package com.pankaj.parking

import java.util.*

class MockWriter {
    var commandsReceived: Int = 0
    private val outputs = Stack<String>()
    val capturedOutput: String?
        get() = outputs.peek()

    fun write(text: String) {
        outputs.push(text)
        commandsReceived++
    }

    fun waitUntilProcessed(numberOfCommands: Int) {
        while (commandsReceived != numberOfCommands){
            Thread.sleep(100)
        }
    }

    fun pop(): String {
        return outputs.pop()
    }
}