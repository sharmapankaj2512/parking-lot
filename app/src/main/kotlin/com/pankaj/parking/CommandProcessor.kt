package com.pankaj.parking

class CommandProcessor(
    private val reader: () -> String,
    private val writer: (String) -> Unit,
    private val parkingLotFactory: ParkingLotFactory) {

    fun process() {
        val command = reader().toLowerCase()
        when {
            CREATE_COMMAND.matches(command) -> processCreateCommand(command)
        }
    }

    private fun processCreateCommand(command: String) {
        CREATE_COMMAND.matchEntire(command)?.let { result ->
            val (numberOfSlots) = result.destructured
            kotlin.runCatching { parkingLot = parkingLotFactory.make(numberOfSlots.toInt()) }
                .onSuccess { writer( Messages.PARKING_LOT_CREATED(parkingLot!!.numberOfSlots)) }
                .onFailure { writer(it.message!!) }
        }
    }

    companion object {
        private val CREATE_COMMAND = """^create_parking_lot\s+(-?[0-9]+)$""".toRegex()
        private var parkingLot: ParkingLot? = null
    }
}
