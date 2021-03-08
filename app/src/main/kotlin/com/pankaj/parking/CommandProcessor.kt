package com.pankaj.parking

class CommandProcessor(
    private val reader: () -> String,
    private val writer: (String) -> Unit,
    private val parkingLotFactory: ParkingLotFactory
) {

    val dispatcher = FillFirstParkingLotDispatcher(parkingLotFactory)

    fun process() {
        val command = reader().toLowerCase()
        when {
            CREATE_COMMAND.matches(command) -> processCreateCommand(command)
            PARK_COMMAND.matches(command) -> processParkCommand(command)
            STATUS_COMMAND.matches(command) -> processStatusCommand(command)
            LEAVE_COMMAND.matches(command) -> processLeaveCommand(command)
            DISPATCH_RULE_COMMAND.matches(command) -> processDispatchCommand(command)
        }
    }

    private fun processCreateCommand(command: String) {
        CREATE_COMMAND.matchEntire(command)?.let { result ->
            val (numberOfSlots) = result.destructured
            kotlin.runCatching { dispatcher.createParkingLot(numberOfSlots.toInt()) }
                .onSuccess { writer(Messages.PARKING_LOT_CREATED(numberOfSlots.toInt())) }
                .onFailure { writer(it.message!!) }
        }
    }

    private fun processParkCommand(command: String) {
        PARK_COMMAND.matchEntire(command)?.let { result ->
            val (registrationNumber, color) = result.destructured
            kotlin.runCatching { dispatcher.next().park(Car(registrationNumber, color)) }
                .onSuccess { slotNumber -> writer(Messages.SLOT_ALLOCATED(slotNumber)) }
                .onFailure { writer(it.message!!) }
        }
    }

    private fun processStatusCommand(command: String) {
        STATUS_COMMAND.matchEntire(command)?.let {
            writer(dispatcher.next().statusReport())
        }
    }

    private fun processLeaveCommand(command: String) {
        LEAVE_COMMAND.matchEntire(command)?.let { result ->
            val (slotNumber) = result.destructured
            kotlin.runCatching { dispatcher.next().leave(slotNumber.toInt()) }
                .onSuccess { writer(Messages.SLOT_FREED(slotNumber.toInt())) }
                .onFailure { writer(Messages.INVALID_SLOT_NUMBER) }
        }
    }

    private fun processDispatchCommand(command: String) {
        DISPATCH_RULE_COMMAND.matchEntire(command)?.let { result ->
            val (rule) = result.destructured
            when (rule) {
                "fill_first" -> writer(Messages.DISPATCHER_SET_TO_FILL_FIRST)
            }
        }
    }

    companion object {
        private val CREATE_COMMAND = """^create_parking_lot\s+(-?[0-9]+)$""".toRegex()
        private val PARK_COMMAND = """^park\s+(.+)\s+(\bwhite\b|\bblack\b)$""".toRegex()
        private val LEAVE_COMMAND = """^leave\s+(-?[0-9]+)$""".toRegex()
        private val STATUS_COMMAND = """^status$""".toRegex()
        private val DISPATCH_RULE_COMMAND = """^dispatch_rule\s+(\beven_distribution\b|\bfill_first\b)$""".toRegex()
        private var parkingLot: ParkingLot? = null
    }
}
