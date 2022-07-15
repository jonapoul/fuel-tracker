package com.jonapoul.fueltracker.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.jonapoul.common.core.PrefPair
import com.jonapoul.common.domain.getString
import com.jonapoul.fueltracker.data.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FuelTrackerPreferences @Inject constructor(
    private val prefs: SharedPreferences,
    private val flowSharedPreferences: FlowSharedPreferences,
) {
    val currency: Currency
        get() = Currency.fromAcronym(prefs.getString(CURRENCY))

    val currencyFlow: Flow<Currency>
        get() = flowSharedPreferences.getString(CURRENCY)
            .asFlow()
            .map(Currency::fromAcronym)

    private inline fun <reified T> SharedPreferences.putIfNotNull(pref: PrefPair<T>, value: T?) {
        edit {
            when (value) {
                null -> return
                is String -> putString(pref.key, value)
                is Int -> putInt(pref.key, value)
                is Long -> putLong(pref.key, value)
                is Boolean -> putBoolean(pref.key, value)
                else -> error("Unexpected type: ${T::class.java.simpleName}")
            }
        }
    }

    companion object {
        val CURRENCY = PrefPair(key = "currency", default = Currency.default().acronym)
    }
}
