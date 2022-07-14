package com.jonapoul.fueltracker.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.jonapoul.common.ui.CommonFragment
import com.jonapoul.common.ui.collectFlow
import com.jonapoul.common.ui.view.setText
import com.jonapoul.common.ui.viewbinding.viewBinding
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.data.db.RefuelEntity
import com.jonapoul.fueltracker.domain.InputMode
import com.jonapoul.fueltracker.input.R
import com.jonapoul.fueltracker.input.databinding.FragmentInputBinding
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Instant

@AndroidEntryPoint
class InputFragment : CommonFragment(layout = R.layout.fragment_input, menu = null) {
    override val binding by viewBinding(FragmentInputBinding::bind)
    private val viewModel by viewModels<InputViewModel>()

    private val mode by lazy {
        arguments?.getParcelable<InputMode>(KEY_INPUT_MODE)
            ?: error("Null input mode from arguments!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setTitle(mode.toolbarTitle)
        viewModel.setMode(mode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseDateTimeView()
        collectFlow(viewModel.existingData, ::onExistingData)
        collectFlow(viewModel.currency, ::onCurrency)
    }

    private fun initialiseDateTimeView() {
        if (mode == InputMode.Create) {
            binding.dateTime.instant = Instant.now()
        }
    }

    private fun onExistingData(entity: RefuelEntity) {
        with(binding) {
            dateTime.instant = entity.time
            distanceDriven.setText(entity.distanceDriven)
        }
    }

    private fun onCurrency(currency: Currency) {
        binding.pricePerVolume.prefixText = currency.symbol
        binding.totalCost.prefixText = currency.symbol
    }

    private fun TextInputLayout.getStringOrNullIfBlank(): String? =
        editText?.text?.toString()?.ifBlank { null }

    private companion object {
        const val KEY_INPUT_MODE = "inputMode"
    }
}
