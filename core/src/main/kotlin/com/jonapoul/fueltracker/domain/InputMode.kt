package com.jonapoul.fueltracker.domain

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.jonapoul.fueltracker.core.R
import kotlinx.parcelize.Parcelize

@Keep
sealed class InputMode(
    @StringRes val toolbarTitle: Int,
) : Parcelable {
    @Keep
    @Parcelize
    object Create : InputMode(R.string.toolbar_create)

    @Keep
    @Parcelize
    data class Edit(val entityId: Long) : InputMode(R.string.toolbar_edit)
}
