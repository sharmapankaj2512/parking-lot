package com.pankaj.parking

class Car(val registrationNumber: String, val color: Color) {
    constructor(registrationNumber: String, color: String) : this(registrationNumber, Color.from(color))

    override fun equals(other: Any?): Boolean {
        return registrationNumber == (other as Car).registrationNumber
    }

    override fun hashCode(): Int {
        return registrationNumber.hashCode()
    }
}
