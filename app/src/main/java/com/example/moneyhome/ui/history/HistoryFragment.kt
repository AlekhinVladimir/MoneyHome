package com.example.moneyhome.ui.history

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneyhome.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    _binding = FragmentHistoryBinding.inflate(inflater, container, false)
    return binding.root
}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация адаптера для RecyclerView
        historyAdapter = HistoryAdapter(
            onDeleteClickListener = { transactionId ->
                showDeleteConfirmationDialog(transactionId)
            }
        )
        binding.recyclerViewHistory.adapter = historyAdapter
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(context)

        // Наблюдение за изменениями в списке операций
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            historyAdapter.submitList(transactions)
        }

        // Загрузка данных из ViewModel
        viewModel.loadTransactions()
    }
    // Метод для показа диалога подтверждения удаления
    private fun showDeleteConfirmationDialog(transactionId: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удаление операции")
            .setMessage("Вы действительно хотите удалить эту операцию?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteTransaction(transactionId)
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}