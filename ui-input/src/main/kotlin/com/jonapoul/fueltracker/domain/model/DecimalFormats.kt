package com.jonapoul.fueltracker.domain.model

import java.text.DecimalFormat

internal object DecimalFormats {
    val ONE_DP = DecimalFormat("0.#")
    val TWO_DP = DecimalFormat("0.##")
    val THREE_DP = DecimalFormat("0.###")
}
