package com.jonapoul.fueltracker.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.jonapoul.common.core.IBuildConfig
import com.jonapoul.common.core.ifTrue
import com.jonapoul.fueltracker.data.db.FuelTrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesDatabaseModule {
    @Provides
    @Singleton
    fun database(
        @ApplicationContext context: Context,
        buildConfig: IBuildConfig,
    ): FuelTrackerDatabase =
        Room.databaseBuilder(context, FuelTrackerDatabase::class.java, "fuel.db")
            .ifTrue(buildConfig.debug) { fallbackToDestructiveMigration() }
            .ifTrue(Debug.isDebuggerConnected()) { allowMainThreadQueries() }
            .build()
}
