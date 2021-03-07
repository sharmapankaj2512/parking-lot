package com.pankaj.parking

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.concurrent.TimeUnit

@Nested
class LeaveSlotFeature: FeatureSetup() {
    @Nested
    @DisplayName("Give a parking lot")
    inner class GivenParkingLot {

        @BeforeEach
        fun beforeEach() {
            reader.createParkingLot(6)
        }

        @Nested
        @DisplayName("And a car parked")
        inner class AndCarParked {

            @BeforeEach
            fun beforeEach() {
                reader.parkCar("MH-10-G-4321", "white")
            }

            @Nested
            @DisplayName("When leave slot request is received")
            inner class WhenLeaveCarRequestIsReceived {

                @BeforeEach
                fun beforeEach() {
                    reader.leaveSlot(1)
                    reader.showStatus()
                }

                @Test
                @DisplayName("Then the slot is freed")
                @Timeout(value = 2, unit = TimeUnit.SECONDS)
                fun thenTheSlotIsFreed() {
                    writer.waitUntilProcessed(4)

                    assertEquals("Slot No.\tRegistration No\tColor", writer.pop())
                    assertEquals(Messages.SLOT_FREED(1), writer.pop())
                }
            }
        }
    }
}