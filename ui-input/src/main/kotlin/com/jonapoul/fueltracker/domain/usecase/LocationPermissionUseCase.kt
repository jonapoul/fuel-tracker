package com.jonapoul.fueltracker.domain.usecase

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.core.location.LocationListenerCompat
import com.jonapoul.common.core.hasPermission
import com.jonapoul.common.core.requireMessage
import com.jonapoul.common.ui.SnackbarFeed
import com.jonapoul.common.ui.SnackbarMessage
import com.jonapoul.common.ui.notifier.Notifier
import com.jonapoul.fueltracker.data.LocationState
import com.jonapoul.fueltracker.domain.InputTextCreator
import com.jonapoul.fueltracker.input.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

internal class LocationPermissionUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationManager: LocationManager,
    private val snackbarFeed: SnackbarFeed,
    private val textCreator: InputTextCreator,
) {
    private val mutableAddress = MutableStateFlow<Address?>(null)

    private val geocoder = Geocoder(context, Locale.getDefault())

    private val locationListener = object : LocationListenerCompat {
        override fun onLocationChanged(location: Location) {
            Timber.v("onLocationChanged $location")
            mutableAddress.value = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                MAX_GEOCODER_RESULTS,
            ).firstOrNull()

            /* We only care about the first result */
            locationManager.removeUpdates(this)
        }
    }

    fun fetchLocation(): Flow<LocationState> =
        flow { emitLocationStates() }
            .distinctUntilChanged()
            .onEach(::snackbarIfRelevant)

    private suspend fun FlowCollector<LocationState>.emitLocationStates() {
        try {
            if (!isGpsEnabled()) {
                Timber.v("GPS disabled")
                emit(LocationState.Disabled)
                return
            }

            if (!hasLocationPermission()) {
                Timber.v("no permission, requesting")
                emit(LocationState.PermissionsRequired(PERMISSIONS))
                return
            }

            emit(LocationState.Finding)

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_UPDATE_TIME_MS,
                MIN_UPDATE_DISTANCE_M,
                locationListener,
            )

            mutableAddress.filterNotNull().collect {
                val locationText = textCreator.addressToString(it)
                Timber.d("Collected address = $it, locationText = $locationText")
                emit(LocationState.Success(locationText))
            }
        } catch (e: Exception) {
            Timber.v(e, "Problem!")
            emit(LocationState.Failure(e.requireMessage()))
        }
    }

    private suspend fun snackbarIfRelevant(state: LocationState) {
        snackbarFeed.add(
            when (state) {
                LocationState.Disabled ->
                    SnackbarMessage.Caution(
                        textCreator.gpsDisabled,
                        Notifier.Action(
                            actionText = R.string.gps_disabled_action,
                            onClick = { launchLocationSettings() },
                        ),
                    )
                is LocationState.Failure ->
                    SnackbarMessage.Warning(state.errorMessage)
                else -> return
            },
        )
    }

    private fun hasLocationPermission(): Boolean =
        PERMISSIONS.all { context.hasPermission(it) }

    private fun isGpsEnabled(): Boolean =
        locationManager.allProviders.any { locationManager.isProviderEnabled(it) }

    private fun launchLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private companion object {
        const val MIN_UPDATE_TIME_MS = 5000L
        const val MIN_UPDATE_DISTANCE_M = 20.0f
        const val MAX_GEOCODER_RESULTS = 1

        val PERMISSIONS = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }
}
