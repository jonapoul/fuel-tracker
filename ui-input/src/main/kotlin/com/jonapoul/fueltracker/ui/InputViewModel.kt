package com.jonapoul.fueltracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.domain.InputMode
import com.jonapoul.fueltracker.domain.ObserveCurrencyUseCase
import com.jonapoul.fueltracker.domain.model.FetchEntityResult
import com.jonapoul.fueltracker.domain.usecase.FetchEntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class InputViewModel @Inject constructor(
    observeCurrencyUseCase: ObserveCurrencyUseCase,
    private val fetchEntityUseCase: FetchEntityUseCase,
) : ViewModel() {

    private val databaseEntity = MutableStateFlow<RefuelEntity?>(null)
    private val draftEntity = MutableStateFlow<RefuelEntity?>(null)

    val existingData: Flow<RefuelEntity> =
        combine(databaseEntity, draftEntity) { db, draft -> draft ?: db }
            .filterNotNull()

    val currency: Flow<Currency> =
        combine(
            databaseEntity,
            observeCurrencyUseCase.selectedCurrency,
        ) { dbEntity, prefsCurrency ->
            dbEntity?.currency ?: prefsCurrency
        }

    fun setMode(mode: InputMode) {
        Timber.d("setMode $mode")
        if (mode is InputMode.Edit) {
            viewModelScope.launch {
                val result = fetchEntityUseCase.fetchEntity(mode.entityId)
                val entity = if (result is FetchEntityResult.Success) {
                    result.entity
                } else null
                databaseEntity.value = entity
                if (draftEntity.value == null) {
                    draftEntity.value = entity
                }
            }
        }
    }
}
