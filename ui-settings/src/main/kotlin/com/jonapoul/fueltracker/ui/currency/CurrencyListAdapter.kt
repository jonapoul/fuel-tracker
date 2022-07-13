package com.jonapoul.fueltracker.ui.currency

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jonapoul.fueltracker.data.Currency
import com.jonapoul.fueltracker.settings.databinding.PrefDialogCurrencyItemBinding

internal class CurrencyListAdapter(
    private val context: Context,
    private val selectedIndex: Int,
    private val onClickItem: (position: Int) -> Unit,
) : BaseAdapter() {
    override fun getCount(): Int =
        Currency.values().size

    override fun getItem(position: Int): Any =
        Currency.values()[position]

    override fun getItemId(position: Int): Long =
        position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currency = getItem(position) as Currency
        val inflater = LayoutInflater.from(context)
        return PrefDialogCurrencyItemBinding.inflate(inflater).apply {
            radioButton.isChecked = position == selectedIndex
            currencyFlag.setImageResource(currency.flagIcon)
            currencyAcronym.text = currency.acronym
            root.setOnClickListener { onClickItem.invoke(position) }
        }.root
    }
}
