package com.example.moneyhome.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyhome.data.local.entity.Expense
import com.example.moneyhome.data.local.entity.Income
import com.example.moneyhome.data.repositiry.ExpenseRepository
import com.example.moneyhome.data.repositiry.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    val expenses: Flow<List<Expense>> = expenseRepository.getAllExpenses()
    val incomes: Flow<List<Income>> = incomeRepository.getAllIncomes()

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.delete(expense)
        }
    }

    fun deleteIncome(income: Income) {
        viewModelScope.launch {
            incomeRepository.delete(income)
        }
    }
}