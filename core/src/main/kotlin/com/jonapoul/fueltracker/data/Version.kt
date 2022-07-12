package com.jonapoul.fueltracker.data

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object Version {
    @ChecksSdkIntAtLeast(parameter = 0)
    fun isAtLeast(sdkInt: Int): Boolean =
        Build.VERSION.SDK_INT >= sdkInt

    @ChecksSdkIntAtLeast(parameter = 0)
    fun isBelow(sdkInt: Int): Boolean =
        Build.VERSION.SDK_INT < sdkInt
}
