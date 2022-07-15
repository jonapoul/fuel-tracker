package com.jonapoul.fueltracker.domain

import android.content.Context
import com.jonapoul.common.domain.TextCreator
import com.jonapoul.fueltracker.input.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class InputTextCreator @Inject constructor(
    @ApplicationContext context: Context,
) : TextCreator(context) {

    val failedFinalisingEntity: String =
        fromRes(R.string.failed_finalising)

    fun failedFetchingEntity(message: String): String =
        context.getString(R.string.failed_fetching, message)
}
