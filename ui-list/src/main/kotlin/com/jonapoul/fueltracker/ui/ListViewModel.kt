package com.jonapoul.fueltracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.domain.ListItem
import com.jonapoul.fueltracker.domain.model.EntityField
import com.jonapoul.fueltracker.domain.usecase.DeleteDataUseCase
import com.jonapoul.fueltracker.domain.usecase.FetchDataUseCase
import com.jonapoul.fueltracker.domain.usecase.ListNavigationUseCase
import com.jonapoul.fueltracker.domain.usecase.ObserveListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ListViewModel @Inject constructor(
    private val observeListUseCase: ObserveListUseCase,
    private val navigationUseCase: ListNavigationUseCase,
    private val fetchDataUseCase: FetchDataUseCase,
    private val deleteDataUseCase: DeleteDataUseCase,
) : ViewModel() {
    val items: Flow<List<ListItem>> =
        observeListUseCase.items

    fun getSortingField(): EntityField =
        observeListUseCase.getSortingField()

    fun setSortingField(field: EntityField) {
        observeListUseCase.setSortingField(field)
    }

    fun editItem(navController: NavController, entityId: Long) {
        navigationUseCase.editItem(navController, entityId)
    }

    fun createItem(navController: NavController) {
        navigationUseCase.createItem(navController)
    }

    suspend fun getEntity(entityId: Long): RefuelEntity? =
        fetchDataUseCase.fetchEntity(entityId)

    fun deleteItem(entityId: Long) {
        viewModelScope.launch {
            deleteDataUseCase.deleteItem(entityId)
        }
    }
}
