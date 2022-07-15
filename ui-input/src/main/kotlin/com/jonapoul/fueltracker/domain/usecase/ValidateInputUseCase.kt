package com.jonapoul.fueltracker.domain.usecase

import com.jonapoul.fueltracker.data.model.Miles
import com.jonapoul.fueltracker.data.model.MilesPerGallon
import com.jonapoul.fueltracker.data.model.MilesPerHour
import com.jonapoul.fueltracker.data.model.miles
import com.jonapoul.fueltracker.data.model.mpg
import com.jonapoul.fueltracker.data.model.mph
import javax.inject.Inject

internal class ValidateInputUseCase @Inject constructor() {
    fun validateDistance(string: String): Miles? =
        if (validate(string, DISTANCE_REGEX)) {
            string.toDouble().miles
        } else null

    fun validateMileage(string: String): MilesPerGallon? =
        if (validate(string, MILEAGE_REGEX)) {
            string.toDouble().mpg
        } else null

    fun validateSpeed(string: String): MilesPerHour? =
        if (validate(string, SPEED_REGEX)) {
            string.toDouble().mph
        } else null

    fun validateTotalCost(string: String): Double? =
        if (validate(string, TOTAL_COST_REGEX)) {
            string.toDouble()
        } else null

    fun validateCostPer(string: String): Double? =
        if (validate(string, COST_PER_REGEX)) {
            string.toDouble()
        } else null

    fun validateVendor(string: String): String? =
        string

    fun validateLocation(string: String): String? =
        string

    private fun validate(string: String, regex: Regex): Boolean =
        string.isNotBlank() && regex.matches(string.trim())

    private companion object {
        val DISTANCE_REGEX = "\\d*(\\.\\d{0,3})?".toRegex()
        val MILEAGE_REGEX = "\\d*(\\.\\d{0,1})?".toRegex()
        val SPEED_REGEX = "\\d*(\\.\\d{0,1})?".toRegex()
        val TOTAL_COST_REGEX = "\\d*(\\.\\d{0,2})?".toRegex()
        val COST_PER_REGEX = "\\d*(\\.\\d{0,3})?".toRegex()
    }
}
