package com.example.moneyhome.ui.add

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneyhome.R
import com.example.moneyhome.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {

    private val viewModel: AddViewModel by viewModels()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            val date = binding.datePicker.date
            val category = binding.categoryInput.text.toString()
            val amount = binding.amountInput.text.toString().toDouble()
            val comment = binding.commentInput.text.toString()
            val type = if (binding.radioGroup.checkedRadioButtonId == R.id.radio_income) "Income" else "Expense"

            if (type == "Income") {
                viewModel.insertIncome(date, category, amount, comment)
            } else {
                viewModel.insertExpense(date, category, amount, comment)
            }

            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}