package com.jonapoul.fueltracker.domain

import com.jonapoul.fueltracker.data.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrencyUseCase @Inject constructor(
    preferences: CorePreferences,
) {
    val selectedCurrency: Flow<Currency> =
        preferences.currencyFlow
}
