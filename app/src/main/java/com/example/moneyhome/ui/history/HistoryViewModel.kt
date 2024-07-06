package com.example.moneyhome.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.example.moneyhome.data.repositiry.TransactionRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    application: Application,
    private val transactionRepository: TransactionRepository,
) : AndroidViewModel(application) {
    private val _transactions = MutableLiveData<List<TransactionEntity>>()
    val transactions: LiveData<List<TransactionEntity>> get() = _transactions

    init {
        loadTransactions()
    }
    fun loadTransactions() {
        viewModelScope.launch {
            val transactionList = transactionRepository.getAllTransactions()
            _transactions.postValue(transactionList)
            saveTransactionsToFile(transactionList)
        }
    }
    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transactionId)
            val updatedTransactions = transactionRepository.getAllTransactions()
            _transactions.postValue(updatedTransactions)
            saveTransactionsToFile(updatedTransactions)
        }
    }
    private suspend fun saveTransactionsToFile(transactions: List<TransactionEntity>) {
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val json = gson.toJson(transactions)
            val file = File(getApplication<Application>().filesDir, "transactions.json")
            file.writeText(json)
        }
    }
}