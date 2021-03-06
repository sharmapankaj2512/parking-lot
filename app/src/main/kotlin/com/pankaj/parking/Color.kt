package com.pankaj.parking

enum class Color {
    Black, White;

    companion object {
        fun from(color: String): Color {
            return when {
                color.equals("white", true) -> White
                else -> Black
            }
        }
    }
}
