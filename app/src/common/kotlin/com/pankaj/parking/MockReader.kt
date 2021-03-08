package com.pankaj.parking

class MockReader {
    var index = 0
    var commands = emptyList<String>()

    private fun addCommand(command: String) {
        commands = commands.plus(command)
    }

    fun read(): String {
        if (commands.isEmpty())
            return ""
        if (index >= commands.size)
            return ""
        return commands[index++]
    }

    fun createParkingLot(numberOfSlots: Int) {
        addCommand("create_parking_lot $numberOfSlots")
    }

    fun parkCar(registrationNumber: String, color: String) {
        addCommand("park $registrationNumber $color")
    }

    fun showStatus() {
        addCommand("status")
    }

    fun leaveSlot(slotNumber: Int) {
        addCommand("leave $slotNumber")
    }

    fun setDispatchRuleToFillFirst() {
        addCommand("dispatch_rule fill_first")
    }
}