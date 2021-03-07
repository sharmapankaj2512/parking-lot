package com.pankaj.parking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParkingLotExtensionsTest {
    @Test
    fun `returns header only for an empty parking lot`() {
        val parkingLot = ParkingLot(3)

        assertEquals("Slot No.\tRegistration No\tColor", parkingLot.statusReport())
    }

    @Test
    fun `excludes available slots and includes only non-available slots in the report`() {
        val parkingLot = ParkingLot(3)
        parkingLot.park(Car("MH-10-G-2222", Color.White))
        parkingLot.park(Car("MH-10-G-2223", Color.White))

        assertEquals("Slot No.\tRegistration No\tColor\n1\tMH-10-G-2222\tWhite\n2\tMH-10-G-2223\tWhite", parkingLot.statusReport())
    }
}