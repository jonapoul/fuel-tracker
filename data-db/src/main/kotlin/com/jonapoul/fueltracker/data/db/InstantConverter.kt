package com.jonapoul.fueltracker.data.db

import androidx.room.TypeConverter
import org.threeten.bp.Instant

internal object InstantConverter {
    @TypeConverter
    fun toInstant(ms: Long): Instant =
        Instant.ofEpochMilli(ms)

    @TypeConverter
    fun fromInstant(instant: Instant): Long =
        instant.toEpochMilli()
}
