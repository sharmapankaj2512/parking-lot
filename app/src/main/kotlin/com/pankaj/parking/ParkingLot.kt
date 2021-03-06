package com.pankaj.parking

class ParkingLot(val numberOfSlots: Int) {
    init {
        require(numberOfSlots >= 0) { Messages.INVALID_NUMBER_OF_SLOTS }
    }
}
