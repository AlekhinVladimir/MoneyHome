package com.example.moneyhome.ui.analytics

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneyhome.R
import com.example.moneyhome.databinding.FragmentAnalyticsBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class AnalyticsFragment : Fragment() {
//
//    private lateinit var barChart: BarChart
//    private lateinit var startDateEditText: EditText
//    private lateinit var endDateEditText: EditText
//    private lateinit var typeSpinner: Spinner
//    private lateinit var categorySpinner: Spinner
//    private lateinit var filterButton: Button
//
//    private val viewModel: AnalyticsViewModel by viewModels()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
//
//        barChart = view.findViewById(R.id.barChart)
//        startDateEditText = view.findViewById(R.id.editTextStartDate)
//        endDateEditText = view.findViewById(R.id.editTextEndDate)
//        typeSpinner = view.findViewById(R.id.spinnerType)
//        categorySpinner = view.findViewById(R.id.spinnerCategory)
//        filterButton = view.findViewById(R.id.buttonFilter)
//
//        filterButton.setOnClickListener {
//            applyFilters()
//        }
//
//        viewModel.transactions.observe(viewLifecycleOwner, Observer { transactions ->
//            updateChart(transactions)
//        })
//
//        viewModel.loadAllTransactions()
//
//        return view
//    }
//
//    private fun applyFilters() {
//        val startDate = startDateEditText.text.toString()
//        val endDate = endDateEditText.text.toString()
//        val type = typeSpinner.selectedItem.toString()
//        val category = categorySpinner.selectedItem.toString()
//
//        if (type != "Все") {
//            viewModel.loadTransactionsByType(type)
//        } else if (category != "Все") {
//            viewModel.loadTransactionsByCategory(category)
//        } else {
//            viewModel.loadTransactionsByDateRange(startDate, endDate)
//        }
//    }
//
//    private fun updateChart(transactions: List<Transaction>) {
//        val entries = transactions.mapIndexed { index, transaction ->
//            BarEntry(index.toFloat(), transaction.amount.toFloat())
//        }
//
//        val dataSet = BarDataSet(entries, "Transactions")
//        val barData = BarData(dataSet)
//        barChart.data = barData
//        barChart.invalidate() // refresh
//    }
}