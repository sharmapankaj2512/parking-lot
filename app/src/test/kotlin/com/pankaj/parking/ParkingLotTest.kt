package com.pankaj.parking

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

    @Test
    fun `returns empty list as status for an empty parking lot`() {
        val parkingLot = ParkingLot(2)

        assertEquals(emptyList<Triple<Int, String, String>>(), parkingLot.status())
    }

    @Test
    fun `returns parked car details as status of parking lot`() {
        val parkingLot = ParkingLot(2)
        parkingLot.park(Car("MH-10-G-4444", Color.White))

        assertEquals(listOf(Triple(1, "MH-10-G-4444", "White")), parkingLot.status())
    }

    @Test
    fun `frees occupied slot on leave slot`() {
        val parkingLot = ParkingLot(1)
        parkingLot.park(Car("MH-10-G-4444", Color.White))
        parkingLot.leave(1)

        assertTrue(parkingLot.isAvailable(1))
    }

    @Test
    fun `throws exception on leave slot when the provided slot number is negative`() {
        val parkingLot = ParkingLot(1)

        assertThrows(IllegalArgumentException::class.java) { parkingLot.leave(-1) }
    }

    @Test
    fun `throws exception on leave slot when the provided slot number is greater than the max range`() {
        val parkingLot = ParkingLot(1)

        assertThrows(IllegalArgumentException::class.java) { parkingLot.leave(3) }
    }

    @Test
    fun `returns true when there is at least one empty slot available`() {
        val parkingLot = ParkingLot(1)

        assertTrue(parkingLot.hasAvailableSlot())
    }

    @Test
    fun `returns false when there is no empty slot available`() {
        val parkingLot = ParkingLot(0)

        assertFalse(parkingLot.hasAvailableSlot())
    }
}