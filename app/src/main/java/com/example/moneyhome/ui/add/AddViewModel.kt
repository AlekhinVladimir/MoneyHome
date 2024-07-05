package com.example.moneyhome.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyhome.data.local.entity.TransactionEntity
import com.example.moneyhome.data.repositiry.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactionSavedEvent = MutableLiveData<Unit>()
    val transactionSavedEvent: LiveData<Unit> get() = _transactionSavedEvent

    fun saveTransaction(date: Date, type: String, category: String, amount: Double, comment: String) {
        viewModelScope.launch {
            val transaction = TransactionEntity(
                date = date,
                type = type,
                category = category,
                amount = amount,
                comment = comment
            )
            repository.insertTransaction(transaction)
            _transactionSavedEvent.postValue(Unit)
        }
    }
    suspend fun getAllTransactions(): List<TransactionEntity> {
        return repository.getAllTransactions()
    }
}
