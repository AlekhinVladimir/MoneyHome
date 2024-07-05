package com.example.moneyhome.ui.history

import androidx.lifecycle.ViewModel
import com.example.moneyhome.data.repositiry.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

//    val transaction: Flow<List<TransactionDB>> = transactionRepository.getAllTransactions()
//
//    fun deleteTransaction(transaction: Transaction) {
//        viewModelScope.launch {
//            transactionRepository.deleteTransaction(transaction)
//        }
//    }

}