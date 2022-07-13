package com.jonapoul.fueltracker.di

import android.content.Context
import com.jonapoul.about.di.AboutResources
import com.jonapoul.fueltracker.about.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesAboutResourcesModule {
    @Provides
    fun resources(
        @ApplicationContext context: Context,
    ): AboutResources = object : AboutResources {
        override val logDirectory: File =
            File(context.dataDir, "logs")

        override val appName: Int =
            R.string.app_name

        override val appIconResource: Int =
            R.mipmap.ic_launcher_round

        override val githubReleasesUrl: String =
            "/repos/jonapoul/fuel-tracker/releases"

        override val githubIssuesUrl: String =
            "https://github.com/jonapoul/fuel-tracker/issues/new"

        override val githubUrl: String =
            "https://github.com/jonapoul/fuel-tracker"

        override val developerName: String =
            "Jon Poulton"

        @Suppress("MagicNumber")
        override val developmentYear: Int =
            2022

        override val softwareLicense: String =
            "Apache 2.0"

        override val logDescriptionText: String =
            context.getString(R.string.log_description_text)

        override val showLogsButton: Boolean =
            true

        override fun readLicensesJsonString(): String =
            context.assets
                .open(LICENSES_ASSET_FILENAME)
                .reader()
                .use { it.readText() }

        override fun logZipFilename(timestamp: String): String =
            "fueltracker_logs_$timestamp.zip"
    }

    private companion object {
        const val LICENSES_ASSET_FILENAME = "open_source_licenses.json"
    }
}
