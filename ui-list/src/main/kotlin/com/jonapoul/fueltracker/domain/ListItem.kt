package com.jonapoul.fueltracker.domain

import androidx.annotation.StringRes

internal data class ListItem(
    val entityId: Long,
    val date: String,
    @StringRes val fieldTitle: Int,
    val fieldValue: String,
)
