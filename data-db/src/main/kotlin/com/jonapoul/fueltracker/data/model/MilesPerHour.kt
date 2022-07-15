package com.jonapoul.fueltracker.data.model

import androidx.room.TypeConverter

data class MilesPerHour(val mph: Double) : Comparable<MilesPerHour> {
    override fun compareTo(other: MilesPerHour): Int =
        mph.compareTo(other.mph)

    override fun toString(): String =
        "$mph mph"

    object Converter {
        @TypeConverter
        fun toMilesPerHour(mph: Double): MilesPerHour =
            MilesPerHour(mph)

        @TypeConverter
        fun toDouble(mph: MilesPerHour): Double =
            mph.mph
    }
}

val Double.mph: MilesPerHour get() = MilesPerHour(this)
val Float.mph: MilesPerHour get() = MilesPerHour(toDouble())
val Int.mph: MilesPerHour get() = MilesPerHour(toDouble())
