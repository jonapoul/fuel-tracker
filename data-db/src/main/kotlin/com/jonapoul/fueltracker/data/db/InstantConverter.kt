package com.jonapoul.fueltracker.data.db

import androidx.room.TypeConverter
import org.threeten.bp.Instant

internal object InstantConverter {
    @TypeConverter
    fun toInstant(string: String): Instant =
        Instant.parse(string)

    @TypeConverter
    fun fromInstant(instant: Instant): String =
        instant.toString()
}
