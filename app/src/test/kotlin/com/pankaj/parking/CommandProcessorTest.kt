package com.pankaj.parking

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class CommandProcessorTest {
    private val reader = MockReader()
    private val writer = MockWriter()
    private val parkingLotFactory = mockk<ParkingLotFactory>()
    private val processor = CommandProcessor(reader::read, writer::write, parkingLotFactory)

    @Test
    fun `parses valid create parking lot command`() {
        every { parkingLotFactory.make(any()) } answers { ParkingLot(firstArg()) }

        reader.createParkingLot(1)
        processor.process()

        assertEquals(Messages.PARKING_LOT_CREATED(1), writer.capturedOutput)
    }

    @Test
    fun `reports error for create command with negative number of slots`() {
        every { parkingLotFactory.make(any()) } throws IllegalArgumentException("Invalid number of slots")

        reader.createParkingLot(1)
        processor.process()

        assertEquals(Messages.INVALID_NUMBER_OF_SLOTS, writer.capturedOutput)
    }

}