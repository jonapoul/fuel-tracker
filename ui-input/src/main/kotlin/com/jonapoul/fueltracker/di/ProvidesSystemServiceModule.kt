package com.jonapoul.fueltracker.di

import android.content.Context
import android.location.LocationManager
import androidx.core.content.getSystemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesSystemServiceModule {
    @Provides
    fun location(
        @ApplicationContext context: Context,
    ): LocationManager = context.getSystemService()!!
}
