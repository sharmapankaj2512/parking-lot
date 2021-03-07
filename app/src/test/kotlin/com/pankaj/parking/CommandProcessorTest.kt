package com.pankaj.parking

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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

    @Test
    fun `parses valid park car command`() {
        val parkingLot = mockk<ParkingLot>()
        val capturedCar = slot<Car>()

        every { parkingLotFactory.make(any()) } returns parkingLot
        every { parkingLot.numberOfSlots } returns 1
        every { parkingLot.park(capture(capturedCar)) } returns 1

        reader.createParkingLot(1)
        reader.parkCar("MH-10-G-1000", "white")
        processor.process()
        processor.process()

        assertEquals(Messages.SLOT_ALLOCATED(1), writer.capturedOutput)
        assertEquals(capturedCar.captured.color, Color.White)
        assertEquals(capturedCar.captured.registrationNumber, "mh-10-g-1000")
    }

    @Test
    fun `reports error on car park when the parking is full`() {
        val parkingLot = mockk<ParkingLot>()

        every { parkingLotFactory.make(any()) } returns parkingLot
        every { parkingLot.numberOfSlots } returns 1
        every { parkingLot.park(any()) } throws IllegalArgumentException(Messages.PARKING_FULL)

        reader.createParkingLot(1)
        reader.parkCar("MH-10-G-1000", "white")
        processor.process()
        processor.process()

        assertEquals(Messages.PARKING_FULL, writer.capturedOutput)
    }

    @Test
    fun `parses valid status command`() {
        val parkingLot = mockk<ParkingLot>()
        val capturedCar = slot<Car>()

        every { parkingLotFactory.make(any()) } returns parkingLot
        every { parkingLot.numberOfSlots } returns 1
        every { parkingLot.park(capture(capturedCar)) } returns 1
        every { parkingLot.status() } returns listOf(Triple(1, "MH-10-G-1000", "white"))

        reader.createParkingLot(1)
        reader.parkCar("MH-10-G-1000", "white")
        reader.showStatus()
        processor.process()
        processor.process()
        processor.process()

        assertEquals("Slot No.\tRegistration No\tColor\n1\tMH-10-G-1000\twhite", writer.capturedOutput)
    }

    @Test
    fun `parses valid leave command`() {
        val parkingLot = mockk<ParkingLot>()
        val capturedCar = slot<Car>()

        every { parkingLotFactory.make(any()) } returns parkingLot
        every { parkingLot.numberOfSlots } returns 1
        every { parkingLot.park(capture(capturedCar)) } returns 1
        every { parkingLot.leave(1) } just runs

        reader.createParkingLot(1)
        reader.parkCar("MH-10-G-1000", "white")
        reader.leaveSlot(1)
        processor.process()
        processor.process()
        processor.process()

        assertEquals(Messages.SLOT_FREED(1), writer.capturedOutput)
    }

    @Test
    fun `reports error for invalid leave command`() {
        val parkingLot = mockk<ParkingLot>()
        val capturedCar = slot<Car>()

        every { parkingLotFactory.make(any()) } returns parkingLot
        every { parkingLot.numberOfSlots } returns 1
        every { parkingLot.park(capture(capturedCar)) } returns 1
        every { parkingLot.leave(-1) } throws IllegalArgumentException(Messages.INVALID_SLOT_NUMBER)

        reader.createParkingLot(1)
        reader.parkCar("MH-10-G-1000", "white")
        reader.leaveSlot(-1)
        processor.process()
        processor.process()
        processor.process()

        assertEquals(Messages.INVALID_SLOT_NUMBER, writer.capturedOutput)
    }
}