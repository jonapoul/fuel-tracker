package com.jonapoul.fueltracker.domain.usecase

import androidx.navigation.NavController
import com.jonapoul.common.domain.safelyNavigate
import com.jonapoul.fueltracker.domain.DashboardNavDirections
import javax.inject.Inject

internal class DashboardNavigationUseCase @Inject constructor(
    private val directions: DashboardNavDirections
) {
    fun navigateToAbout(navController: NavController) {
        navController.safelyNavigate(
            directions.toAbout()
        )
    }

    fun navigateToSettings(navController: NavController) {
        navController.safelyNavigate(
            directions.toSettings()
        )
    }
}
