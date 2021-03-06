package com.pankaj.parking

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.concurrent.TimeUnit

@Nested
class ParkCarFeature: FeatureSetup() {

    @Nested
    @DisplayName("Give a parking lot")
    inner class GivenParkingLot {

        @BeforeEach
        fun beforeEach() {
            reader.createParkingLot(1)
        }

        @Nested
        @DisplayName("When a car park request is received")
        inner class WhenCarParkRequestIsReceived {

            @BeforeEach
            fun beforeEach() {
                reader.parkCar("MH-10-G-2222", "white")
            }

            @Test
            @Timeout(value = 2, unit = TimeUnit.SECONDS)
            @DisplayName("Then the car should be allocated an available slot")
            fun thenAnAvailableSlotShouldBeAllocated() {
                writer.waitUntilProcessed(2)

                assertEquals(Messages.SLOT_ALLOCATED(1), writer.capturedOutput)
            }
        }
    }
}