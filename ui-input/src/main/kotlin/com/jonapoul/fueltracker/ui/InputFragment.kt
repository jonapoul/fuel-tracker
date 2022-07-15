package com.jonapoul.fueltracker.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.jonapoul.common.ui.CommonFragment
import com.jonapoul.common.ui.collectFlow
import com.jonapoul.common.ui.dialogs.setSimpleNegativeButton
import com.jonapoul.common.ui.dialogs.setSimplePositiveButton
import com.jonapoul.common.ui.dialogs.showCautionDialog
import com.jonapoul.common.ui.view.enableIf
import com.jonapoul.common.ui.view.setText
import com.jonapoul.common.ui.viewbinding.viewBinding
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.domain.InputMode
import com.jonapoul.fueltracker.domain.model.DecimalFormats
import com.jonapoul.fueltracker.input.R
import com.jonapoul.fueltracker.input.databinding.FragmentInputBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import java.text.DecimalFormat

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
        viewModel.setMode(mode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setTitle(mode.toolbarTitle)

        initialiseBackButton()
        initialiseValidation()
        initialiseSaveButton()
        fetchExistingData()
        collectFlow(viewModel.currency, ::onCurrency)
        collectFlow(viewModel.enableSaveButton, ::onEnableSaveButton)
    }

    private fun initialiseBackButton() {
        val activity = requireActivity() as AppCompatActivity
        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner) { onBackPressed() }
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_cross)
        }
    }

    private fun onBackPressed() {
        showCautionDialog(
            message = getString(R.string.back_dialog_message),
            title = getString(R.string.back_dialog_title),
            extraConfig = {
                setSimplePositiveButton { navController.navigateUp() }
                setSimpleNegativeButton()
            },
        )
    }

    private fun initialiseValidation() {
        with(binding) {
            distanceDriven.setUpValidation(viewModel::validateDistanceDriven)
            distanceRemaining.setUpValidation(viewModel::validateDistanceLeft)
            mileage.setUpValidation(viewModel::validateMileage)
            speed.setUpValidation(viewModel::validateSpeed)
            totalCost.setUpValidation(viewModel::validateTotalCost)
            costPerVolume.setUpValidation(viewModel::validateCostPer)
            vendor.setUpValidation(viewModel::validateVendor)
            location.setUpValidation(viewModel::validateLocation)
        }
    }

    private fun initialiseSaveButton() {
        binding.saveButton.setOnClickListener {
            viewModel.saveData(
                onSuccess = {
                    navController.navigateUp()
                },
            )
        }
    }

    private fun fetchExistingData() {
        var job: Job? = null
        job = collectFlow(viewModel.existingData) { entity ->
            with(binding) {
                dateTime.instant = entity.time
                setTextIfNotNull(entity.distanceDriven?.miles, DECIMAL_FORMAT_DISTANCE, distanceDriven)
                setTextIfNotNull(entity.distanceRemaining?.miles, DECIMAL_FORMAT_DISTANCE, distanceRemaining)
                setTextIfNotNull(entity.mileage?.mpg, DECIMAL_FORMAT_MILEAGE, mileage)
                setTextIfNotNull(entity.averageSpeed?.mph, DECIMAL_FORMAT_MILEAGE, speed)
                setTextIfNotNull(entity.totalCost, DECIMAL_FORMAT_TOTAL, totalCost)
                setTextIfNotNull(entity.costPerVolume, DECIMAL_FORMAT_PER, costPerVolume)
                vendor.setText(entity.vendor)
                location.setText(entity.location)
            }
            /* Stop observing this flow after the first emission */
            job?.cancel()
        }
    }

    private fun onCurrency(currency: Currency) {
        binding.costPerVolume.prefixText = currency.symbol
        binding.totalCost.prefixText = currency.symbol
    }

    private fun onEnableSaveButton(enableSaveButton: Boolean) {
        binding.saveButton.enableIf(enableSaveButton)
    }

    private fun TextInputLayout.setUpValidation(
        validateInput: (String) -> Boolean,
    ) {
        editText?.addTextChangedListener(
            onTextChanged = { string, _, _, _ ->
                val trimmed = string?.trim()?.toString() ?: ""
                val isValid = validateInput.invoke(trimmed)
                error = if (isValid) {
                    null
                } else {
                    context.getString(R.string.invalid_input)
                }
            },
        )
    }

    private fun setTextIfNotNull(double: Double?, format: DecimalFormat, textInputLayout: TextInputLayout) {
        val string = double?.let { format.format(it) }
        if (string != null) {
            textInputLayout.setText(string)
        }
    }

    private companion object {
        const val KEY_INPUT_MODE = "inputMode"
        val DECIMAL_FORMAT_DISTANCE = DecimalFormats.TWO_DP
        val DECIMAL_FORMAT_MILEAGE = DecimalFormats.ONE_DP
        val DECIMAL_FORMAT_TOTAL = DecimalFormats.TWO_DP
        val DECIMAL_FORMAT_PER = DecimalFormats.THREE_DP
    }
}
