package com.jonapoul.fueltracker.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupActionBarWithNavController
import com.jonapoul.common.ui.SnackbarMessage
import com.jonapoul.common.ui.collectFlow
import com.jonapoul.common.ui.navControllers
import com.jonapoul.common.ui.notifier.Notifier
import com.jonapoul.common.ui.viewbinding.viewBinding
import com.jonapoul.fueltracker.R
import com.jonapoul.fueltracker.databinding.ActivityFuelTrackerBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
internal class FuelTrackerActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityFuelTrackerBinding::inflate)
    private val viewModel by viewModels<FuelTrackerActivityViewModel>()
    private val navController by navControllers(R.id.nav_host_fragment)

    private val notifier = Notifier(
        successColour = R.color.onSnackbarGreen,
        infoColour = R.color.onSnackbarBlue,
        cautionColour = R.color.onSnackbarOrange,
        warningColour = R.color.onSnackbarRed,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)

        /* Set the view and initialise navigation via the app bar */
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        /* Receive and display/cancel any snackbar messages */
        collectFlow(viewModel.snackbars, ::onSnackbarMessage)
    }

    private fun onSnackbarMessage(snackbar: SnackbarMessage?) {
        Timber.d("onSnackbarMessage $snackbar")
        snackbar?.notify?.invoke(notifier, binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp(): Boolean {
        Timber.d("onSupportNavigateUp")
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
