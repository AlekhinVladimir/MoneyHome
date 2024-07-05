package com.example.moneyhome.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Transaction
import com.example.moneyhome.R
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.example.moneyhome.databinding.FragmentAddBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


@AndroidEntryPoint
class AddFragment : Fragment() {

    private val viewModel: AddViewModel by viewModels()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val dateStr = binding.editTextDate.text.toString()
            val type = if (binding.radioGroupOperationType.checkedRadioButtonId == R.id.radioButtonIncome) "Доход" else "Расход"
            val category = binding.spinnerCategory.selectedItem.toString()
            val amount = binding.editTextAmount.text.toString().toDoubleOrNull() ?: 0.0
            val comment = binding.editTextComment.text.toString()

            if (dateStr.isEmpty() || amount == 0.0 || amount == 0.0 || category.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля корректно", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            try {
                val parsedDate = formatter.parse(dateStr)
                if (parsedDate != null) {
                    viewModel.saveTransaction(parsedDate, type, category, amount, comment)
                    lifecycleScope.launch {
                        saveTransactionsToFile(viewModel.getAllTransactions())
                    }
                } else {
                    Toast.makeText(requireContext(), "Неправильный формат даты", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ParseException) {
                Toast.makeText(requireContext(), "Неправильный формат даты", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.transactionSavedEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Транзакция сохранена", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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