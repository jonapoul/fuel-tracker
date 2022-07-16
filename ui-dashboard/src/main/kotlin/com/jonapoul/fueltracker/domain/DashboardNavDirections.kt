package com.jonapoul.fueltracker.domain

import androidx.navigation.NavDirections

interface DashboardNavDirections {
    fun toInput(mode: InputMode): NavDirections
    fun toList(): NavDirections
    fun toAbout(): NavDirections
    fun toSettings(): NavDirections
}
