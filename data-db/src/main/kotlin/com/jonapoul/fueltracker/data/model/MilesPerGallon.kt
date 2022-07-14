package com.jonapoul.fueltracker.data.model

import androidx.room.TypeConverter

data class MilesPerGallon(val mpg: Double) : Comparable<MilesPerGallon> {
    override fun compareTo(other: MilesPerGallon): Int =
        mpg.compareTo(other.mpg)

    override fun toString(): String =
        "$mpg mpg"

    object Converter {
        @TypeConverter
        fun toMilesPerGallon(mpg: Double): MilesPerGallon =
            MilesPerGallon(mpg)

        @TypeConverter
        fun toDouble(mpg: MilesPerGallon): Double =
            mpg.mpg
    }
}

val Double.mpg: MilesPerGallon get() = MilesPerGallon(this)
val Float.mpg: MilesPerGallon get() = MilesPerGallon(toDouble())
val Int.mpg: MilesPerGallon get() = MilesPerGallon(toDouble())
