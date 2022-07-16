package com.jonapoul.fueltracker.domain.usecase

import androidx.navigation.NavController
import com.jonapoul.common.domain.safelyNavigate
import com.jonapoul.fueltracker.domain.ListNavDirections
import javax.inject.Inject

internal class ListNavigationUseCase @Inject constructor(
    private val directions: ListNavDirections,
) {
    fun editItem(navController: NavController, entityId: Long) {
        navController.safelyNavigate(
            directions.toEdit(entityId),
        )
    }

    fun createItem(navController: NavController) {
        navController.safelyNavigate(
            directions.toCreate(),
        )
    }
}
