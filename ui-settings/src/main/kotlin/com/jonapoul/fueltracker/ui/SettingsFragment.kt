package com.jonapoul.fueltracker.ui

import androidx.fragment.app.viewModels
import com.jonapoul.common.ui.CommonPreferenceFragment
import com.jonapoul.fueltracker.settings.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : CommonPreferenceFragment(R.xml.settings, menu = null) {
    private val viewModel by viewModels<SettingsViewModel>()
}
