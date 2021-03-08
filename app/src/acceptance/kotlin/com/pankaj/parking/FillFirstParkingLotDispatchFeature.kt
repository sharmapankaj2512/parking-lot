package com.pankaj.parking

import org.junit.jupiter.api.*
import java.util.concurrent.TimeUnit

@Nested
class FillFirstParkingLotDispatchFeature: FeatureSetup() {

    @Nested
    @DisplayName("Given one full and one empty parking lots")
    inner class GivenOneFullAndOneEmptyParkingLots {

        @BeforeEach
        fun beforeEach() {
            reader.createParkingLot(1)
            reader.createParkingLot(0)
        }

        @Nested
        @DisplayName("And dispatch rule set to fill first")
        inner class AndDispatchRuleSetToFillFirst {

            @BeforeEach
            fun beforeEach() {
                reader.setDispatchRuleToFillFirst()
            }

            @Nested
            @DisplayName("When park a car request is received")
            inner class WhenParkCarRequestIsReceived {

                @BeforeEach
                fun beforeEach() {
                    reader.parkCar("MH-10-G-2333", "white")
                }

                @Test
                @DisplayName("Then the car is allocated a slot from the empty parking lot")
                @Timeout(value = 2, unit = TimeUnit.SECONDS)
                fun thenTheCarIsAllocatedASlotFromTheEmptyParkingLot() {
                    writer.waitUntilProcessed(4)

                    //add assertion on status
                    // update message to include parking lot number
                    Assertions.assertEquals(Messages.SLOT_ALLOCATED(1), writer.pop())
                    Assertions.assertEquals(Messages.DISPATCHER_SET_TO_FILL_FIRST, writer.pop())
                }
            }
        }
    }
}