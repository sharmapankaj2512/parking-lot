package com.pankaj.parking

fun ParkingLot.statusReport(): String {
    val header = "Slot No.\tRegistration No\tColor"
    val rows = status().joinToString("\n") { "${it.first}\t${it.second}\t${it.third}" }
    return header.plus("\n").plus(rows).trimEnd();
}