package com.jonapoul.fueltracker.data

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.jonapoul.fueltracker.core.R
import timber.log.Timber
import java.util.Locale

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
        fun default(): Currency {
            val countryCode = Locale.getDefault().country.uppercase()
            Timber.d("default $countryCode")
            return when (countryCode) {
                "GB" -> GBP
                "US" -> USD
                "AU" -> AUD
                "NZ" -> NZD
                "CA" -> CAD
                else -> if (EUROZONE_NATION_CODES.contains(countryCode)) EUR else USD // fallback
            }
        }

        /* Austria, Belgium, Cyprus, Estonia, Finland, France, Germany, Greece, Ireland, Italy, Latvia, Lithuania,
         * Luxembourg, Malta, Netherlands, Portugal, Slovakia, Slovenia, and Spain */
        private val EUROZONE_NATION_CODES = listOf(
            "AT", "BE", "CY", "EE", "FI", "FR", "DE", "GR", "IE", "IT",
            "LV", "LT", "LU", "MT", "NL", "PT", "SK", "SI", "ES",
        )

        fun fromAcronym(currencyName: String): Currency =
            values().first { it.acronym == currencyName }
    }
}
