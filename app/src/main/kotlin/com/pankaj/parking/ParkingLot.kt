package com.pankaj.parking

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

    fun status(): List<Triple<Int, String, String>> {
        return slots.filterNot { it.isAvailable() }.map { it.status() }
    }

    fun leave(slotNumber: Int) {
        require(slotNumber in 0..numberOfSlots) { Messages.INVALID_SLOT_NUMBER }
        slots.find { it.number == slotNumber }?.leave()
    }

    fun hasAvailableSlot(): Boolean {
        return slots.any { it.isAvailable() }
    }

    inner class ParkingSlot(val number: Int, var car: Car? = null) {
        fun isAvailable(): Boolean {
            return car == null
        }

        fun park(car: Car): Int {
            this.car = car
            return number
        }

        fun status(): Triple<Int, String, String> {
            return Triple(number, car!!.registrationNumber, car!!.color.toString())
        }

        fun leave() {
            this.car = null
        }
    }
}
