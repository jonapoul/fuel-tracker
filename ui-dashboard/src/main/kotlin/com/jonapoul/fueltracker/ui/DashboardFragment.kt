package com.jonapoul.fueltracker.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.jonapoul.common.ui.CommonFragment
import com.jonapoul.common.ui.viewbinding.viewBinding
import com.jonapoul.fueltracker.dashboard.R
import com.jonapoul.fueltracker.dashboard.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : CommonFragment(layout = R.layout.fragment_dashboard, menu = R.menu.menu_dashboard) {
    override val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel by viewModels<DashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createButton.setOnClickListener { viewModel.createNewEntry(navController) }
        binding.viewAllButton.setOnClickListener { viewModel.navigateToList(navController) }
    }

    override fun onMenuItemSelected(menuItemId: Int): Boolean {
        when (menuItemId) {
            R.id.action_settings -> viewModel.navigateToSettings(navController)
            R.id.action_about -> viewModel.navigateToAbout(navController)
            else -> return false
        }
        return true
    }
}
