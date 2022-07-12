package com.jonapoul.fueltracker

import androidx.multidex.MultiDexApplication
import com.jonapoul.common.domain.init.AppInitialisers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class FuelTrackerApplication : MultiDexApplication() {

    @Inject
    lateinit var initialisers: AppInitialisers

    override fun onCreate() {
        super.onCreate()
        initialisers.init(app = this)
    }
}
