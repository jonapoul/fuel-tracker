package com.jonapoul.fueltracker.data

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.jonapoul.fueltracker.core.R

@Keep
enum class Currency(
    val symbol: String,
    val acronym: String,
    @DrawableRes val flagIcon: Int,
) {
    GBP(
        symbol = "£",
        acronym = "GBP",
        flagIcon = R.drawable.flag_uk,
    ),
    USD(
        symbol = "$",
        acronym = "USD",
        flagIcon = R.drawable.flag_us,
    ),
    EUR(
        symbol = "€",
        acronym = "EUR",
        flagIcon = R.drawable.flag_eu,
    ),
    AUD(
        symbol = "$",
        acronym = "AUD",
        flagIcon = R.drawable.flag_au,
    ),
    NZD(
        symbol = "$",
        acronym = "NZD",
        flagIcon = R.drawable.flag_nz,
    ),
    CAD(
        symbol = "$",
        acronym = "CAD",
        flagIcon = R.drawable.flag_ca,
    );

    override fun toString(): String =
        acronym

    companion object {
        val DEFAULT = GBP

        fun fromAcronym(currencyName: String): Currency =
            values().first { it.acronym == currencyName }
    }
}
