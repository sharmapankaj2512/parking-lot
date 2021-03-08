package com.pankaj.parking

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class FillFirstParkingLotDispatcherTest {

    @Test
    fun `creates a new parking lot`() {
        val factory = mockk<ParkingLotFactory>()
        val dispatcher = FillFirstParkingLotDispatcher(factory)
        val capturedNumberOfSlots = slot<Int>()

        every { factory.make(capture(capturedNumberOfSlots)) } answers { ParkingLot(firstArg()) }

        dispatcher.createParkingLot(6)

        assertEquals(6, capturedNumberOfSlots.captured)
    }

    @Test
    fun `returns parking lot which has at least one empty slot`() {
        val factory = mockk<ParkingLotFactory>()
        val parkingLot1 = mockk<ParkingLot>()
        val parkingLot2 = mockk<ParkingLot>()
        val parkingLot3 = mockk<ParkingLot>()
        val dispatcher = FillFirstParkingLotDispatcher(factory)

        every { factory.make(any()) } returns parkingLot1 andThen parkingLot2 andThen parkingLot3
        every { parkingLot1.hasAvailableSlot() } returns false
        every { parkingLot2.hasAvailableSlot() } returns true
        every { parkingLot3.hasAvailableSlot() } returns false

        dispatcher.createParkingLot(0)
        dispatcher.createParkingLot(1)
        dispatcher.createParkingLot(0)

        assertEquals(parkingLot2, dispatcher.next())
    }

    @Test
    fun `throws exception when no parking lot with free slot is available`() {
        val factory = mockk<ParkingLotFactory>()
        val parkingLot = mockk<ParkingLot>()
        val dispatcher = FillFirstParkingLotDispatcher(factory)

        every { factory.make(any()) } returns parkingLot
        every { parkingLot.hasAvailableSlot() } returns false

        assertThrows(IllegalArgumentException::class.java) {
            dispatcher.next()
        }
    }
}