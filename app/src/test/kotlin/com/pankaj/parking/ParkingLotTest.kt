package com.pankaj.parking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ParkingLotTest {
    @Test
    fun `throws exception when number of slots is negative`() {
        val ex = assertThrows(IllegalArgumentException::class.java) { ParkingLot(-1) }

        assertEquals(Messages.INVALID_NUMBER_OF_SLOTS, ex.message)
    }
}