package com.jonapoul.fueltracker.di

import android.content.SharedPreferences
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.jonapoul.common.di.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesPreferencesModule {
    @Provides
    fun flowSharedPreferences(
        sharedPreferences: SharedPreferences,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): FlowSharedPreferences = FlowSharedPreferences(
        sharedPreferences,
        ioDispatcher
    )
}
