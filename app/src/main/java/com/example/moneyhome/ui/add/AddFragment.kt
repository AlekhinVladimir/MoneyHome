package com.example.moneyhome.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moneyhome.R
import com.example.moneyhome.data.local.ShowDatePickerDialog
import com.example.moneyhome.databinding.FragmentAddBinding
import com.example.moneyhome.domain.entity.TransactionEntity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AddFragment : Fragment() {

    private val viewModel: AddViewModel by viewModels()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextDate.setText(dateFormat.format(Calendar.getInstance().time))
        binding.editTextDate.doAfterTextChanged { text ->
            if (isDateFormatValid(text) && isDateReal(text)) {
                binding.editTextDate.error = null
                binding.buttonSave.isEnabled = true
            } else {
                binding.editTextDate.error = "Неверная или нереальная дата"
                binding.buttonSave.isEnabled = false
            }
        }

        binding.calendarDaySelectorFrame.setOnClickListener {
            fun onDateSelected(s: String) {
                binding.editTextDate.setText(s)
            }
            ShowDatePickerDialog.showDatePicker(requireContext(), null, ::onDateSelected)
        }

        binding.buttonSave.setOnClickListener {
            saveTransaction()
        }

        viewModel.transactionSavedEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Запись сохранена", Toast.LENGTH_SHORT).show()
            clearInputFields()
        }
    }
    private fun isDateReal(dateString: CharSequence?): Boolean {
        val date = dateFormat.parse(dateString.toString())
        val currentDate = Calendar.getInstance().time
        return date != null && date.before(currentDate)
    }
    private fun isDateFormatValid(dateString: CharSequence?): Boolean {
        val dateParts = dateString?.split(".") ?: return false

        if (dateParts.size != 3) {
            return false
        }
        val day = dateParts.getOrNull(0)?.toIntOrNull()
        val month = dateParts.getOrNull(1)?.toIntOrNull()
        val year = dateParts.getOrNull(2)?.toIntOrNull()
        if (day == null || month == null || year == null) {
            return false
        }
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> day in 1..31
            4, 6, 9, 11 -> day in 1..30
            2 -> day in 1..if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            else -> false
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun clearInputFields() {
        with(binding) {
            editTextDate.setText("")
            editTextAmount.setText("")
            editTextComment.setText("")
            spinnerCategory.setSelection(0)
            radioGroupOperationType.clearCheck()
        }
    }

    private fun saveTransaction() {
        val dateStr = binding.editTextDate.text.toString()
        val type = if (binding.radioGroupOperationType.checkedRadioButtonId == R.id.radioButtonIncome) "Доход" else "Расход"
        val category = binding.spinnerCategory.selectedItem.toString()
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
        val comment = binding.editTextComment.text.toString()
        if (dateStr.isEmpty() || amount == 0.0 || category.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля корректно", Toast.LENGTH_SHORT).show()
            return
        }
        val parsedDate = dateFormat.parse(dateStr) // Уже проверено на этапе ввода
        if (parsedDate == null) {
            Toast.makeText(requireContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.saveTransaction(parsedDate, type, category, amount, comment)
        lifecycleScope.launch {
            saveTransactionsToFile(viewModel.getAllTransactions())
        }
    }
    private suspend fun saveTransactionsToFile(transactions: List<TransactionEntity>) {
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val json = gson.toJson(transactions)
            val file = File(requireContext().filesDir, "transactions.json")
            file.writeText(json)
        }
    }
}