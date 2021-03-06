package com.pankaj.parking

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.concurrent.TimeUnit

@Nested
class CreateParkingFixture {
    private val reader = MockReader()
    private val writer = MockWriter()
    private val app = TestApp(reader, writer)

    @BeforeEach
    fun beforeEach() {
        app.start()
    }

    @AfterEach
    fun afterEach() {
        app.stop()
    }

    @Nested
    @DisplayName("Given no parking lot exists")
    internal inner class GivenNoParkingLot {

        @Nested
        @DisplayName("When create parking lot request is received")
        inner class WhenParkingLotRequestIsReceived {

            @BeforeEach
            fun beforeEach() {
                reader.createParkingLot(2)
            }

            @Test
            @DisplayName("then a new parking lot of given size is created")
            @Timeout(value = 2, unit = TimeUnit.SECONDS)
            fun thenParkingLotOfGivenSizeIsCreated() {
                writer.waitUntilProcessed(1)

                assertEquals(Messages.PARKING_LOT_CREATED(2), writer.capturedOutput)
            }
        }
    }
}