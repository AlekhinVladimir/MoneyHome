package com.example.moneyhome.ui.analytics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyhome.domain.entity.TransactionEntity
import com.example.moneyhome.domain.repositiry.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
    private val _transactions = MutableLiveData<List<TransactionEntity>>()
    val transactions: LiveData<List<TransactionEntity>> = _transactions
    fun loadAllTransactions() {
        viewModelScope.launch {
            val result = repository.getAllTransactions()
            _transactions.postValue(result)
            Log.d("AnalyticsViewModel", "Loaded ${result.size} transactions")
        }
    }
    fun loadTransactionsByDateRange(startDate: String, endDate: String) {
        viewModelScope.launch {
            _transactions.postValue(repository.getTransactionsByDateRange(startDate, endDate))
        }
    }
    fun loadTransactionsByType(type: String) {
        viewModelScope.launch {
            _transactions.postValue(repository.getTransactionsByType(type))
        }
    }
    fun loadTransactionsByCategory(category: String) {
        viewModelScope.launch {
            _transactions.postValue(repository.getTransactionsByCategory(category))
        }
    }
    fun loadTransactionsByCategoryAndType(category: String, type: String) {
        viewModelScope.launch {
            _transactions.postValue(repository.getTransactionsByCategoryAndType(category, type))
        }
    }

    fun loadTransactionsByDateRangeandCategory(
        startDate: String,
        endDate: String,
        category: String
    ) {
        viewModelScope.launch {
            _transactions.postValue(repository.getTransactionsByDateRangeandCategory(startDate, endDate, category))
        }
    }
}