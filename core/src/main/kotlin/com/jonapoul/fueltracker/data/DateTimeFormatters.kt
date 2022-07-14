package com.jonapoul.fueltracker.data

import com.jonapoul.common.data.localised
import org.threeten.bp.format.DateTimeFormatter

val String.localisedFormatter: DateTimeFormatter
    get() = DateTimeFormatter.ofPattern(this).localised()
