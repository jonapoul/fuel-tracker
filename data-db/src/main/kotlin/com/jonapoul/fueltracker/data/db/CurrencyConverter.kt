package com.jonapoul.fueltracker.data.db

import androidx.room.TypeConverter
import com.jonapoul.fueltracker.data.Currency

object CurrencyConverter {
    @TypeConverter
    fun toCurrency(string: String): Currency =
        Currency.fromAcronym(string)

    @TypeConverter
    fun toString(currency: Currency): String =
        currency.acronym
}
