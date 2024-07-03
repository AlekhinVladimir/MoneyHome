package com.example.moneyhome.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyhome.data.local.entity.Expense
import com.example.moneyhome.data.local.entity.Income
import com.example.moneyhome.data.repositiry.ExpenseRepository
import com.example.moneyhome.data.repositiry.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    fun insertExpense(date: Long, category: String, amount: Double, comment: String) {
        val expense = Expense(date =date, category = category, amount = amount, comment = comment)
        viewModelScope.launch {
            expenseRepository.insert(expense)
        }
    }

    fun insertIncome(date: Long, category: String, amount: Double, comment: String) {
        val income = Income(date = date, category = category, amount = amount, comment = comment)
        viewModelScope.launch {
            incomeRepository.insert(income)
        }
    }
}