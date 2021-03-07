package com.pankaj.parking

object Messages {
    val INVALID_NUMBER_OF_SLOTS = "Invalid number of slots"
    val INVALID_SLOT_NUMBER = "Invalid slot number"
    val PARKING_LOT_CREATED = { numberOfSlots: Int -> "Created a parking lot with $numberOfSlots slots" }
    val PARKING_FULL: String = "Cannot park as the parking lot is full"
    val SLOT_ALLOCATED = { slotNumber: Int -> "Allocated slot number: $slotNumber" }
    val SLOT_FREED = { slotNumber: Int -> "Slot number $slotNumber is free"}
}
