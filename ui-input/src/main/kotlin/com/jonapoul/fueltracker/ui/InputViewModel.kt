package com.jonapoul.fueltracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.data.DraftRefuelEntity
import com.jonapoul.fueltracker.domain.InputMode
import com.jonapoul.fueltracker.domain.ObserveCurrencyUseCase
import com.jonapoul.fueltracker.domain.model.FetchEntityResult
import com.jonapoul.fueltracker.domain.usecase.FetchEntityUseCase
import com.jonapoul.fueltracker.domain.usecase.ValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class InputViewModel @Inject constructor(
    observeCurrencyUseCase: ObserveCurrencyUseCase,
    private val fetchEntityUseCase: FetchEntityUseCase,
    private val validateInputUseCase: ValidateInputUseCase,
) : ViewModel() {

    private val draft = MutableStateFlow<DraftRefuelEntity?>(null)

    val enableSaveButton: Flow<Boolean> =
        draft.map {
            it?.distanceDriven != null && it.distanceRemaining != null &&
                it.mileage != null && it.averageSpeed != null && it.totalCost != null &&
                it.costPerVolume != null && it.currency != null
        }

    val existingData: Flow<DraftRefuelEntity> =
        draft.asStateFlow().filterNotNull()

    val currency: Flow<Currency> =
        combine(draft, observeCurrencyUseCase.selectedCurrency) { draft, prefsCurrency ->
            draft?.currency ?: prefsCurrency
        }.onEach {
            draft.value = draft.value?.copy(currency = it)
        }

    fun setMode(mode: InputMode) {
        Timber.d("setMode $mode")
        /* Don't do anything if we've already got a draft entity on the go */
        if (draft.value != null) return

        when (mode) {
            is InputMode.Edit -> {
                /* Fetch data from the DB and store it in the relevant flow. Also set the draft
                * entity to match that one */
                viewModelScope.launch {
                    val result = fetchEntityUseCase.fetchEntity(mode.entityId)
                    val entity = if (result is FetchEntityResult.Success) result.entity else null
                    draft.value = DraftRefuelEntity.fromEntity(entity)
                }
            }
            InputMode.Create -> {
                /* Just create a new empty entity */
                draft.value = DraftRefuelEntity.empty()
            }
        }
    }

    fun validateDistanceDriven(string: String): Boolean =
        validateInputUseCase.validateDistance(string)
            .updateDraft { copy(distanceDriven = it) }

    fun validateDistanceLeft(string: String): Boolean =
        validateInputUseCase.validateDistance(string)
            .updateDraft { copy(distanceRemaining = it) }

    fun validateMileage(string: String): Boolean =
        validateInputUseCase.validateMileage(string)
            .updateDraft { copy(mileage = it) }

    fun validateSpeed(string: String): Boolean =
        validateInputUseCase.validateSpeed(string)
            .updateDraft { copy(averageSpeed = it) }

    fun validateTotalCost(string: String): Boolean =
        validateInputUseCase.validateTotalCost(string)
            .updateDraft { copy(totalCost = it) }

    fun validateCostPer(string: String): Boolean =
        validateInputUseCase.validateCostPer(string)
            .updateDraft { copy(costPerVolume = it) }

    fun validateVendor(string: String): Boolean =
        validateInputUseCase.validateVendor(string)
            .updateDraft { copy(vendor = it) }

    fun validateLocation(string: String): Boolean =
        validateInputUseCase.validateLocation(string)
            .updateDraft { copy(location = it) }

    private fun <T : Any> T?.updateDraft(block: DraftRefuelEntity.(T?) -> DraftRefuelEntity): Boolean {
        draft.value = draft.value?.block(this)
            .also { Timber.v("Updated to $it") }
        return this != null
    }
}
