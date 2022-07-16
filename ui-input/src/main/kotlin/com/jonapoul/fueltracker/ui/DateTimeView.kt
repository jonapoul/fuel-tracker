package com.jonapoul.fueltracker.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.jonapoul.fueltracker.data.localisedFormatter
import com.jonapoul.fueltracker.input.R
import com.jonapoul.fueltracker.input.databinding.ViewDateTimeBinding
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class DateTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val binding = ViewDateTimeBinding.inflate(
        LayoutInflater.from(context), this, true,
    )

    private lateinit var selectedTime: LocalTime
    private lateinit var selectedDate: LocalDate
    private var onChangeListener: ((Instant) -> Unit)? = null

    var instant: Instant
        get() {
            val zdt = ZonedDateTime.of(selectedDate, selectedTime, ZoneId.systemDefault())
            return zdt.toInstant()
        }
        set(value) {
            setTime(LocalTime.from(value.zoned))
            setDate(LocalDate.from(value.zoned))
            onChangeListener?.invoke(instant)
        }

    init {
        binding.timePickerButton.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTitleText(R.string.time_picker_title)
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setNegativeButtonText(android.R.string.cancel)
                .setPositiveButtonText(android.R.string.ok)
                .setHour(selectedTime.hour)
                .setMinute(selectedTime.minute)
                .build()
            timePicker.addAllListeners(
                onPositive = { hour, minute ->
                    setTime(LocalTime.of(hour, minute))
                    onChangeListener?.invoke(instant)
                },
                onNegative = { timePicker.dismiss() },
            )
            timePicker.show(findFragment<Fragment>().parentFragmentManager, toString())
        }

        binding.datePickerText.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.date_picker_title)
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setNegativeButtonText(android.R.string.cancel)
                .setPositiveButtonText(android.R.string.ok)
                .setSelection(instant.toEpochMilli())
                .build()
            datePicker.addAllListeners(
                onPositive = {
                    val enteredInstant = Instant.ofEpochMilli(it)
                    setDate(LocalDate.from(enteredInstant.zoned))
                    onChangeListener?.invoke(instant)
                },
                onNegative = { datePicker.dismiss() },
            )
            datePicker.show(findFragment<Fragment>().parentFragmentManager, toString())
        }

        binding.arrowLeft.setOnClickListener {
            setDate(selectedDate.minusDays(1L))
            onChangeListener?.invoke(instant)
        }
        binding.arrowRight.setOnClickListener {
            setDate(selectedDate.plusDays(1L))
            onChangeListener?.invoke(instant)
        }
    }

    fun setChangeListener(onChange: (Instant) -> Unit) {
        onChangeListener = onChange
    }

    private fun setTime(time: LocalTime) {
        selectedTime = time
        binding.timePickerButton.text = TIME_FORMATTER.format(time)
    }

    private fun setDate(date: LocalDate) {
        selectedDate = date
        binding.datePickerText.text = DATE_FORMATTER.format(date)
    }

    private fun <S> MaterialDatePicker<S>.addAllListeners(
        onPositive: (selection: S) -> Unit,
        onNegative: () -> Unit,
    ) {
        addOnPositiveButtonClickListener { onPositive.invoke(it) }
        addOnNegativeButtonClickListener { onNegative.invoke() }
        addOnCancelListener { onNegative.invoke() }
        addOnDismissListener { onNegative.invoke() }
    }

    private fun MaterialTimePicker.addAllListeners(
        onPositive: (hour: Int, minute: Int) -> Unit,
        onNegative: () -> Unit,
    ) {
        addOnPositiveButtonClickListener { onPositive.invoke(hour, minute) }
        addOnNegativeButtonClickListener { onNegative.invoke() }
        addOnCancelListener { onNegative.invoke() }
        addOnDismissListener { onNegative.invoke() }
    }

    private val Instant.zoned: ZonedDateTime
        get() = atZone(ZoneId.systemDefault())

    private companion object {
        val DATE_FORMATTER = "dd/MM/yyyy".localisedFormatter
        val TIME_FORMATTER = "HH:mm".localisedFormatter
    }
}
