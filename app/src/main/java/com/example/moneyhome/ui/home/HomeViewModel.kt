package com.example.moneyhome.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel @ViewModelInject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: IncomeRepository
) : ViewModel() {
    fun navigateToMenu() {
        // navigate to menu fragment
    }
}