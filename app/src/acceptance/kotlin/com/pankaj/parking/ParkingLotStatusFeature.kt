package com.pankaj.parking

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.concurrent.TimeUnit

@Nested
class ParkingLotStatusFeature: FeatureSetup() {
    @Nested
    @DisplayName("Given a parking lot")
    inner class GivenParkingLot {

        @BeforeEach()
        fun beforeEach() {
            reader.createParkingLot(1)
        }

        @Nested
        @DisplayName("And a car parked")
        inner class AndCarParked {

            @BeforeEach
            fun beforeEach() {
                reader.parkCar("MH-10-G-4444", "white")
            }

            @Nested
            @DisplayName("When status of the parking lot is requested")
            inner class WhenStatusIsRequested {

                @BeforeEach
                fun beforeEach() {
                    reader.showStatus()
                }

                @Test
                @DisplayName("then the status report should be returned")
                @Timeout(value = 2, unit = TimeUnit.SECONDS)
                fun thenDisplayStatusReport() {
                    writer.waitUntilProcessed(3)

                    val report = "Slot No.\tRegistration No\tColor\n1\tmh-10-g-4444\tWhite"

                    assertEquals(report, writer.capturedOutput)
                }
            }
        }
    }
}