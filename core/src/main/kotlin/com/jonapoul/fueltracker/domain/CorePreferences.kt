package com.jonapoul.fueltracker.domain

import android.content.SharedPreferences
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.jonapoul.common.core.PrefPair
import com.jonapoul.common.domain.getString
import com.jonapoul.fueltracker.data.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CorePreferences @Inject constructor(
    private val prefs: SharedPreferences,
    private val flowSharedPreferences: FlowSharedPreferences,
) {
    val currency: Currency
        get() = Currency.fromAcronym(prefs.getString(CURRENCY))

    val currencyFlow: Flow<Currency>
        get() = flowSharedPreferences.getString(CURRENCY)
            .asFlow()
            .map(Currency::fromAcronym)

    companion object {
        val CURRENCY = PrefPair(key = "currency", default = Currency.default().acronym)
    }
}
