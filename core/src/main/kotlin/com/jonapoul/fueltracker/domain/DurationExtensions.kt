package com.jonapoul.fueltracker.domain

import kotlin.time.Duration
import kotlin.time.DurationUnit

internal fun DurationUnit.shortName(): String =
    when (this) {
        DurationUnit.SECONDS -> "seconds"
        DurationUnit.MINUTES -> "minutes"
        DurationUnit.HOURS -> "hours"
        DurationUnit.DAYS -> "days"
        else -> error("Unknown unit: $this")
    }

internal fun Duration.largestUnit(): DurationUnit =
    when {
        inWholeDays > 0 -> DurationUnit.DAYS
        inWholeHours > 0 -> DurationUnit.HOURS
        inWholeMinutes > 0 -> DurationUnit.MINUTES
        else -> DurationUnit.SECONDS
    }

internal fun String.toUnit(): DurationUnit =
    when (this) {
        "days" -> DurationUnit.DAYS
        "hours" -> DurationUnit.HOURS
        "minutes" -> DurationUnit.MINUTES
        "seconds" -> DurationUnit.SECONDS
        else -> error("Unknown unit: $this")
    }
