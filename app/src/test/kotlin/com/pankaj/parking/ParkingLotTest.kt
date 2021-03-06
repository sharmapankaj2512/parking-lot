package com.pankaj.parking

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ParkingLotTest {
    @Test
    fun `throws exception when number of slots is negative`() {
        val ex = assertThrows(IllegalArgumentException::class.java) { ParkingLot(-1) }

        assertEquals(Messages.INVALID_NUMBER_OF_SLOTS, ex.message)
    }

    @Test
    fun `allocates first available slot to the parking car`() {
        val parkingLot = ParkingLot(1)

        val slotNumber = parkingLot.park(Car("MH-10-G-2222", Color.White))

        assertEquals(1, slotNumber)
        assertFalse(parkingLot.isAvailable(slotNumber))
    }

    @Test
    fun `throws exception on car park when the parking is already full`() {
        val ex = assertThrows(IllegalArgumentException::class.java) {
            ParkingLot(0).park(Car("MH-10-G-2222", Color.White))
        }
        assertEquals(Messages.PARKING_FULL, ex.message)
    }
}