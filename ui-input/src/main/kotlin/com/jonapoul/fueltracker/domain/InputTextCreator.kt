package com.jonapoul.fueltracker.domain

import android.content.Context
import android.location.Address
import com.jonapoul.common.domain.TextCreator
import com.jonapoul.fueltracker.input.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class InputTextCreator @Inject constructor(
    @ApplicationContext context: Context,
) : TextCreator(context) {

    val failedFinalisingEntity: String
        get() = fromRes(R.string.failed_finalising)

    val gpsDisabled: String
        get() = fromRes(R.string.gps_disabled)

    fun failedFetchingEntity(message: String): String =
        context.getString(R.string.failed_fetching, message)

    fun addressToString(address: Address): String =
        context.getString(
            R.string.address_string,
            address.thoroughfare,
            address.locality,
            address.subAdminArea,
            address.adminArea,
        )
}
