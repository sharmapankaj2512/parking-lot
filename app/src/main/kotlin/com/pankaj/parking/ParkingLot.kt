package com.pankaj.parking

import java.lang.IllegalArgumentException

class ParkingLot(val numberOfSlots: Int) {
    private val slots = (1..numberOfSlots).map { ParkingSlot(it) }

    init {
        require(numberOfSlots >= 0) { Messages.INVALID_NUMBER_OF_SLOTS }
    }

    fun park(car: Car): Int {
        return slots.find { it.isAvailable() }?.park(car) ?: throw IllegalArgumentException(Messages.PARKING_FULL)
    }

    fun isAvailable(slotNumber: Int): Boolean {
        return slots.find { it.number == slotNumber }?.isAvailable() ?: false
    }

    inner class ParkingSlot(val number: Int, var car: Car? = null) {
        fun isAvailable(): Boolean {
            return car == null
        }

        fun park(car: Car): Int {
            this.car = car
            return number
        }
    }
}
