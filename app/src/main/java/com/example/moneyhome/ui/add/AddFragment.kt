package com.example.moneyhome.ui.add

import android.app.DatePickerDialog
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
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.example.moneyhome.databinding.FragmentAddBinding
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
    private val dateRegex = Regex("\\d{2}\\.\\d{2}\\.\\d{4}")

    private val datePickerDialog by lazy {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, month + 1, year)
                binding.editTextDate.setText(selectedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextDate.doAfterTextChanged { text ->
            if (isDateFormatValid(text)) {
                binding.editTextDate.error = null
                binding.buttonSave.isEnabled = true
            } else {
                binding.editTextDate.error = "Неверный формат даты"
                binding.buttonSave.isEnabled = false
            }
        }

        binding.calendarDaySelectorFrame.setOnClickListener {
            datePickerDialog.show()
        }

        binding.buttonSave.setOnClickListener {
            saveTransaction()
        }

        viewModel.transactionSavedEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Запись сохранена", Toast.LENGTH_SHORT).show()
            clearInputFields()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isDateFormatValid(text: CharSequence?): Boolean {
        return text != null && dateRegex.matches(text)
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