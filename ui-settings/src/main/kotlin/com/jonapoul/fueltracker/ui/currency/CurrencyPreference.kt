package com.jonapoul.fueltracker.ui.currency

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.app.AlertDialog
import androidx.preference.ListPreference
import androidx.preference.Preference.SummaryProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jonapoul.common.ui.dialogs.setSimpleNegativeButton
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.settings.R

internal class CurrencyPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.dialogPreferenceStyle,
    defStyleRes: Int = 0,
) : ListPreference(context, attrs, defStyleAttr, defStyleRes) {

    init {
        setDefaultValue(DEFAULT.toString())
        onSetInitialValue(DEFAULT.toString())
    }

    private var currency: Currency = DEFAULT
        get() = Currency.fromAcronym(getPersistedString(DEFAULT.toString()))
        set(value) {
            field = value
            persistString(value.toString())
            notifyChanged()
        }

    init {
        summaryProvider = SummaryProvider<CurrencyPreference> { preference -> preference.currency.acronym }
        val acronyms = Currency.values().map { it.acronym }.toTypedArray()
        entries = acronyms
        entryValues = acronyms
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? =
        a.getString(index)

    override fun onSetInitialValue(defaultValue: Any?) {
        val default = if (defaultValue is String) defaultValue else DEFAULT.toString()
        currency = Currency.fromAcronym(getPersistedString(default))
    }

    override fun setValue(value: String?) {
        super.setValue(value)
        setIcon(currency.flagIcon)
    }

    override fun onClick() {
        val selectedIndex = findIndexOfValue(value)
        var dialog: AlertDialog? = null
        val onItemClickListener: (Int) -> Unit = { index ->
            val currency = Currency.values()[index]
            value = currency.toString()
            setIcon(currency.flagIcon)
            dialog?.dismiss()
        }
        val adapter = CurrencyListAdapter(context, selectedIndex, onItemClickListener)
        dialog = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setAdapter(adapter, null)
            .setSimpleNegativeButton()
            .create()

        dialog.show()
    }

    private companion object {
        val DEFAULT = Currency.default()
    }
}
