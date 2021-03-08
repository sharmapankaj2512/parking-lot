package com.pankaj.parking

import java.lang.IllegalArgumentException

class FillFirstParkingLotDispatcher(private val factory: ParkingLotFactory) {
    private val slots = mutableListOf<ParkingLot>()

    fun createParkingLot(numberOfSlots: Int) {
        slots.add(factory.make(numberOfSlots))
    }

    fun next(): ParkingLot {
        return slots.find { it.hasAvailableSlot() }
            ?: throw IllegalArgumentException(Messages.ALL_PARKING_LOTS_ARE_FULL)
    }
}
