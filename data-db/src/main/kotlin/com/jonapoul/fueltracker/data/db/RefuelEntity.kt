package com.jonapoul.fueltracker.data.db

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.data.model.Miles
import com.jonapoul.fueltracker.data.model.MilesPerGallon
import com.jonapoul.fueltracker.data.model.MilesPerHour
import org.threeten.bp.Instant

@Keep
@Entity
data class RefuelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val time: Instant,
    val distanceDriven: Miles,
    val distanceRemaining: Miles,
    val mileage: MilesPerGallon,
    val averageSpeed: MilesPerHour,
    val totalCost: Double,
    val costPerVolume: Double,
    val vendor: String?,
    val location: String?,
    val currency: Currency,
)
