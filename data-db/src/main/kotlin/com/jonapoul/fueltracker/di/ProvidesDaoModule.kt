package com.jonapoul.fueltracker.di

import com.jonapoul.fueltracker.data.db.FuelTrackerDatabase
import com.jonapoul.fueltracker.data.db.RefuelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesDaoModule {
    @Provides
    fun refuel(db: FuelTrackerDatabase): RefuelDao =
        db.refuelDao()
}
