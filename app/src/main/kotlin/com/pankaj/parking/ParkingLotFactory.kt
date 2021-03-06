package com.pankaj.parking

class ParkingLotFactory {
    fun make(numberOfSlots: Int): ParkingLot {
        return ParkingLot(numberOfSlots)
    }

}
