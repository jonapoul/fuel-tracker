package com.jonapoul.fueltracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonapoul.fueltracker.data.model.Miles
import com.jonapoul.fueltracker.data.model.MilesPerGallon
import com.jonapoul.fueltracker.data.model.MilesPerHour

@Database(
    entities = [
        RefuelEntity::class,
    ],
    version = 1,
    autoMigrations = [],
)
@TypeConverters(
    InstantConverter::class,
    Miles.Converter::class,
    MilesPerHour.Converter::class,
    MilesPerGallon.Converter::class,
)
internal abstract class FuelTrackerDatabase : RoomDatabase() {
    abstract fun refuelDao(): RefuelDao
}
