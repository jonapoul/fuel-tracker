package com.jonapoul.fueltracker.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.jonapoul.fueltracker.domain.usecase.DashboardNavigationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    private val navigationUseCase: DashboardNavigationUseCase,
) : ViewModel() {
    fun createNewEntry(navController: NavController) {
        navigationUseCase.createNewEntry(navController)
    }

    fun navigateToAbout(navController: NavController) {
        navigationUseCase.navigateToAbout(navController)
    }

    fun navigateToSettings(navController: NavController) {
        navigationUseCase.navigateToSettings(navController)
    }
}
