package com.jonapoul.fueltracker.di

import androidx.navigation.NavDirections
import com.jonapoul.fueltracker.domain.DashboardNavDirections
import com.jonapoul.fueltracker.ui.DashboardFragmentDirections
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavDirectionsModule {
    @Provides
    fun dashboard(): DashboardNavDirections = object : DashboardNavDirections {
        override fun toAbout(): NavDirections =
            DashboardFragmentDirections.toAbout()

        override fun toSettings(): NavDirections =
            DashboardFragmentDirections.toSettings()
    }
}
