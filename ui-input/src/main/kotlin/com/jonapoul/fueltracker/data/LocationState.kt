package com.jonapoul.fueltracker.data

internal sealed class LocationState {
    object Disabled : LocationState()
    data class PermissionsRequired(val permissions: List<String>) : LocationState()
    object Finding : LocationState()
    data class Success(val location: String) : LocationState()
    data class Failure(val errorMessage: String) : LocationState()
}
