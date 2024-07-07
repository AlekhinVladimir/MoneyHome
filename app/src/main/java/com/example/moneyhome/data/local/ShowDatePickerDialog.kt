package com.example.moneyhome.data.local

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ShowDatePickerDialog {
    companion object {
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        fun showDatePicker(context: Context, initialDate: Date? = null, onDateSelected: (String) -> Unit) {
            val calendar = Calendar.getInstance()
            if (initialDate != null) {
                calendar.time = initialDate
            }
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    onDateSelected(dateFormat.format(calendar.time))
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
}