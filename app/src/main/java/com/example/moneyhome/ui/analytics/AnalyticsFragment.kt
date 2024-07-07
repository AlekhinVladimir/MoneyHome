package com.example.moneyhome.ui.analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moneyhome.R
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AnalyticsFragment : Fragment() {
    private lateinit var barChart: BarChart
    private lateinit var startDatePicker: DatePicker
    private lateinit var endDatePicker: DatePicker
    private lateinit var typeSpinner: Spinner
    private lateinit var categorySpinner: Spinner
    private lateinit var filterButton: Button
    private val viewModel: AnalyticsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_analytics, container, false)
        initViews(view)
        setupListeners()
        observeTransactions()
        viewModel.loadAllTransactions()
        return view
    }
    private fun initViews(view: View) {
        barChart = view.findViewById(R.id.barChart)
        startDatePicker = view.findViewById(R.id.datePickerStart)
        endDatePicker = view.findViewById(R.id.datePickerEnd)
        typeSpinner = view.findViewById(R.id.spinnerType)
        categorySpinner = view.findViewById(R.id.spinnerCategory)
        filterButton = view.findViewById(R.id.buttonFilter)
    }
    private fun setupListeners() {
        filterButton.setOnClickListener { applyFilters() }
    }
    private fun observeTransactions() {
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            updateChart(transactions)
        }
    }
    private fun applyFilters() {
        val startDate = getStartDate()
        val endDate = getEndDate()
        val type = typeSpinner.selectedItem.toString()
        val category = categorySpinner.selectedItem.toString()
        when {
            type != "Все" && category == "Все" -> viewModel.loadTransactionsByType(type)
            category != "Все" && type == "Все" -> viewModel.loadTransactionsByCategory(category)
            type == "Все" && category == "Все" -> viewModel.loadTransactionsByDateRange(startDate, endDate)
            else -> viewModel.loadTransactionsByCategoryAndType(category, type)
        }
    }
    private fun getStartDate(): String {
        val calendar = Calendar.getInstance()
        calendar.set(startDatePicker.dayOfMonth, startDatePicker.month,startDatePicker.year )
        return calendar.timeInMillis.toString()
    }
    private fun getEndDate(): String {
        val calendar = Calendar.getInstance()
        calendar.set(startDatePicker.dayOfMonth, startDatePicker.month,startDatePicker.year )
        return calendar.timeInMillis.toString()
    }
    private fun updateChart(transactions: List<TransactionEntity>) {
        val entries = transactions.mapIndexed { index, transaction ->
            BarEntry(index.toFloat(), transaction.amount.toFloat())
        }
        val colors = listOf(
            Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.CYAN,
            Color.MAGENTA, Color.GRAY, Color.LTGRAY, Color.DKGRAY, Color.BLACK
        )

        val dataSet = BarDataSet(entries, "Transactions")
        dataSet.colors = colors
        val barData = BarData(dataSet)
        barChart.setFitBars(true)
        barChart.data = barData
        barChart.invalidate()
    }
}