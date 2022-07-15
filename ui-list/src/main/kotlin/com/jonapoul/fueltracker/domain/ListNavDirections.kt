package com.jonapoul.fueltracker.domain

import androidx.navigation.NavDirections

interface ListNavDirections {
    fun toCreate(): NavDirections
    fun toEdit(entityId: Long): NavDirections
}
