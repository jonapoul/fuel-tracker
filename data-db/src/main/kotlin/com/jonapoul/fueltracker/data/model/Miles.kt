package com.jonapoul.fueltracker.data.model

import androidx.room.TypeConverter

data class Miles(val miles: Double) : Comparable<Miles> {
    fun toMetres(): Double =
        miles * METRES_PER_MILE

    override fun compareTo(other: Miles): Int =
        miles.compareTo(other.miles)

    override fun toString(): String =
        "$miles miles"

    object Converter {
        @TypeConverter
        fun toMiles(miles: Double): Miles =
            Miles(miles)

        @TypeConverter
        fun toDouble(miles: Miles): Double =
            miles.miles
    }

    private companion object {
        const val METRES_PER_MILE = 1609.34
    }
}

val Double.miles: Miles get() = Miles(this)
val Float.miles: Miles get() = Miles(toDouble())
val Int.miles: Miles get() = Miles(toDouble())
