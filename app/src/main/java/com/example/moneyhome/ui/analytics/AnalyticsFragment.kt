package com.example.moneyhome.ui.analytics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moneyhome.R
import com.example.moneyhome.data.local.ShowDatePickerDialog
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyticsFragment : Fragment() {
    private lateinit var barChart: BarChart
    private lateinit var startDateText: TextView
    private lateinit var endDateText: TextView
    private lateinit var startDatePicker: ImageButton
    private lateinit var endDatePicker: ImageButton
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
        startDateText = view.findViewById(R.id.textViewStartDate)
        endDateText = view.findViewById(R.id.textViewEndDate)
        startDatePicker = view.findViewById(R.id.calendar_day_selector_1)
        endDatePicker = view.findViewById(R.id.calendar_day_selector_2)
        typeSpinner = view.findViewById(R.id.spinnerType)
        categorySpinner = view.findViewById(R.id.spinnerFilterCategory)
        filterButton = view.findViewById(R.id.buttonFilter)
    }
    private fun setupListeners() {
        startDatePicker.setOnClickListener {ShowDatePickerDialog.showDatePicker(requireContext(),null,::onDateSelected1) }
        endDatePicker.setOnClickListener {ShowDatePickerDialog.showDatePicker(requireContext(),null,::onDateSelected2) }
        filterButton.setOnClickListener { applyFilters() }
    }
    private fun onDateSelected1(selectedDate: String) {
        startDateText.text = selectedDate
    }
    private fun onDateSelected2(selectedDate: String) {
        endDateText.text = selectedDate
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
        return startDateText.text.toString()
    }
    private fun getEndDate(): String {
        return endDateText.text.toString()
    }
    private fun updateChart(transactions: List<TransactionEntity>) {
        val barChart = barChart
        if (transactions.isEmpty()) {
            return
        }
        val entries = transactions.mapIndexed { index, transaction ->
            Log.d("AnalyticsFragment", "Creating BarEntry: index=$index, amount=${transaction.amount}") // Добавлено для отслеживания BarEntry
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