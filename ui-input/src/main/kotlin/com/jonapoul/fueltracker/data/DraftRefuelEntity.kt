package com.jonapoul.fueltracker.data

import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.data.model.Miles
import com.jonapoul.fueltracker.data.model.MilesPerGallon
import com.jonapoul.fueltracker.data.model.MilesPerHour
import org.threeten.bp.Instant

internal data class DraftRefuelEntity(
    val id: Long,
    var time: Instant,
    var distanceDriven: Miles? = null,
    var distanceRemaining: Miles? = null,
    var mileage: MilesPerGallon? = null,
    var averageSpeed: MilesPerHour? = null,
    var totalCost: Double? = null,
    var costPerVolume: Double? = null,
    var vendor: String? = null,
    var location: String? = null,
    val currency: Currency? = null,
) {
    fun toEntityOrNull(): RefuelEntity? = try {
        RefuelEntity(
            id,
            time,
            distanceDriven!!,
            distanceRemaining!!,
            mileage!!,
            averageSpeed!!,
            totalCost!!,
            costPerVolume!!,
            vendor,
            location,
            currency!!,
        )
    } catch (e: NullPointerException) {
        null
    }

    companion object {
        fun empty(): DraftRefuelEntity =
            DraftRefuelEntity(
                id = 0L,
                time = Instant.now(),
            )

        fun fromEntity(entity: RefuelEntity?): DraftRefuelEntity =
            if (entity == null) empty() else DraftRefuelEntity(
                id = entity.id,
                time = entity.time,
                distanceDriven = entity.distanceDriven,
                distanceRemaining = entity.distanceRemaining,
                mileage = entity.mileage,
                averageSpeed = entity.averageSpeed,
                totalCost = entity.totalCost,
                costPerVolume = entity.costPerVolume,
                vendor = entity.vendor,
                location = entity.location,
                currency = entity.currency,
            )
    }
}
