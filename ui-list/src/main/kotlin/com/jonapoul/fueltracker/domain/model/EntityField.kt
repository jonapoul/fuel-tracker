package com.jonapoul.fueltracker.domain.model

import androidx.annotation.StringRes
import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.list.R
import java.text.DecimalFormat

private val TWO_DP = DecimalFormat("0.##")

internal enum class EntityField(
    @StringRes val title: Int,
    val value: RefuelEntity.() -> String,
) {
    DistanceDriven(
        title = R.string.view_distance_driven,
        value = { distanceDriven.toString() },
    ),

    DistanceRemaining(
        title = R.string.view_distance_remaining,
        value = { distanceRemaining.toString() },
    ),

    Mileage(
        title = R.string.view_mileage,
        value = { mileage.toString() },
    ),

    Speed(
        title = R.string.view_speed,
        value = { averageSpeed.toString() },
    ),

    CostTotal(
        title = R.string.view_cost_total,
        value = { "${currency.symbol}${TWO_DP.format(totalCost)}" },
    ),

    CostPerLitre(
        title = R.string.view_cost_per,
        value = { "${currency.symbol}${TWO_DP.format(costPerVolume)}" },
    ),

    Vendor(
        title = R.string.view_vendor,
        value = { vendor ?: "" },
    ),

    Location(
        title = R.string.view_location,
        value = { location ?: "" },
    );
}
