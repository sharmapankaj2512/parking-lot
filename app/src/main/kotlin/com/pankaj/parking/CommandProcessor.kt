package com.pankaj.parking

class CommandProcessor(
    private val reader: () -> String,
    private val writer: (String) -> Unit,
    private val parkingLotFactory: ParkingLotFactory
) {

    fun process() {
        val command = reader().toLowerCase()
        when {
            CREATE_COMMAND.matches(command) -> processCreateCommand(command)
            PARK_COMMAND.matches(command) -> processParkCommand(command)
            STATUS_COMMAND.matches(command) -> processStatusCommand(command)
            LEAVE_COMMAND.matches(command) -> processLeaveCommand(command)
        }
    }

    private fun processCreateCommand(command: String) {
        CREATE_COMMAND.matchEntire(command)?.let { result ->
            val (numberOfSlots) = result.destructured
            kotlin.runCatching { parkingLot = parkingLotFactory.make(numberOfSlots.toInt()) }
                .onSuccess { writer(Messages.PARKING_LOT_CREATED(parkingLot!!.numberOfSlots)) }
                .onFailure { writer(it.message!!) }
        }
    }

    private fun processParkCommand(command: String) {
        PARK_COMMAND.matchEntire(command)?.let { result ->
            val (registrationNumber, color) = result.destructured
            kotlin.runCatching { parkingLot?.park(Car(registrationNumber, color)) }
                .onSuccess { slotNumber -> slotNumber?.let { writer(Messages.SLOT_ALLOCATED(it)) } }
                .onFailure { writer(it.message!!) }
        }
    }

    private fun processStatusCommand(command: String) {
        STATUS_COMMAND.matchEntire(command)?.let {
            writer(parkingLot?.statusReport() ?: "")
        }
    }

    private fun processLeaveCommand(command: String) {
        LEAVE_COMMAND.matchEntire(command)?.let { result ->
            val (slotNumber) = result.destructured
            kotlin.runCatching { parkingLot?.leave(slotNumber.toInt()) }
                .onSuccess { writer(Messages.SLOT_FREED(slotNumber.toInt())) }
                .onFailure { writer(Messages.INVALID_SLOT_NUMBER) }
        }
    }

    companion object {
        private val CREATE_COMMAND = """^create_parking_lot\s+(-?[0-9]+)$""".toRegex()
        private val PARK_COMMAND = """^park\s+(.+)\s+(\bwhite\b|\bblack\b)$""".toRegex()
        private val LEAVE_COMMAND = """^leave\s+(-?[0-9]+)$""".toRegex()
        private val STATUS_COMMAND = """status""".toRegex()
        private var parkingLot: ParkingLot? = null
    }
}
