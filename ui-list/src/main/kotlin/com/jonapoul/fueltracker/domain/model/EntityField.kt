package com.jonapoul.fueltracker.domain.model

import androidx.annotation.StringRes
import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.data.localisedFormatter
import com.jonapoul.fueltracker.list.R
import java.text.DecimalFormat

private val TWO_DP = DecimalFormat("0.##")
private val DATE_FORMATTER = "EE dd MMM yyyy".localisedFormatter

internal enum class EntityField(
    @StringRes val title: Int,
    val value: RefuelEntity.() -> String,
    val sorting: List<RefuelEntity>.() -> List<RefuelEntity>,
) {
    Instant(
        title = R.string.view_instant,
        value = { DATE_FORMATTER.format(instant) },
        sorting = { sortedBy { it.instant } },
    ),

    DistanceDriven(
        title = R.string.view_distance_driven,
        value = { distanceDriven.toString() },
        sorting = { sortedBy { it.distanceDriven } },
    ),

    DistanceRemaining(
        title = R.string.view_distance_remaining,
        value = { distanceRemaining.toString() },
        sorting = { sortedBy { it.distanceRemaining } },
    ),

    Mileage(
        title = R.string.view_mileage,
        value = { mileage.toString() },
        sorting = { sortedBy { it.mileage } },
    ),

    Speed(
        title = R.string.view_speed,
        value = { averageSpeed.toString() },
        sorting = { sortedBy { it.averageSpeed } },
    ),

    CostTotal(
        title = R.string.view_cost_total,
        value = { "${currency.symbol}${TWO_DP.format(totalCost)}" },
        sorting = { sortedBy { it.totalCost } },
    ),

    CostPerLitre(
        title = R.string.view_cost_per,
        value = { "${currency.symbol}${TWO_DP.format(costPerVolume)}" },
        sorting = { sortedBy { it.costPerVolume } },
    ),

    Vendor(
        title = R.string.view_vendor,
        value = { vendor ?: "" },
        sorting = { sortedBy { it.vendor ?: "" } },
    ),

    Location(
        title = R.string.view_location,
        value = { location ?: "" },
        sorting = { sortedBy { it.location ?: "" } },
    );
}
