package com.jonapoul.fueltracker.init

import android.app.Application
import com.jonapoul.common.core.logging.FileLoggingTree
import com.jonapoul.common.domain.init.IAppInitialiser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import timber.log.Timber
import javax.inject.Inject

internal class FileLoggingInitialiser @Inject constructor() : IAppInitialiser {
    override fun init(app: Application) {
        /* Start logging to a file in the app's private storage */
        Timber.plant(FileLoggingTree())
        Timber.d("Started file logging")
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal interface BindsLoggingInitialiserModule {
    @Binds
    @IntoSet
    fun initialiser(bind: FileLoggingInitialiser): IAppInitialiser
}
