package com.pankaj.parking

class App(reader: () -> String, writer: (String) -> Unit) {
    private val process = CommandProcessor(reader, writer, ParkingLotFactory())

    fun start() {
        while (true) {
            process.process()
        }
    }
}

fun main() {
    App({ readLine()!! }, {text: String -> println(text)}).start()
}
