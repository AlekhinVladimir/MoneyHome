package com.example.moneyhome.data.repositiry

import androidx.room.Transaction
import com.example.moneyhome.data.local.dao.TransactionDao
import com.example.moneyhome.data.local.entity.TransactionEntity
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {

    suspend fun insertTransaction(transaction:TransactionEntity) {
        transactionDao.insert(transaction)
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) {
        transactionDao.delete(transaction.id)
    }

    suspend fun getAllTransactions(): List<TransactionEntity> {
        return transactionDao.getAllTransactions()
    }

    suspend fun getTransactionsByDateRange(startDate: String, endDate: String): List<TransactionEntity> {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        val startDateObj = dateFormat.parse(startDate) ?: return emptyList()
        val endDateObj = dateFormat.parse(endDate) ?: return emptyList()

        return transactionDao.getTransactionsByDateRange(startDateObj, endDateObj)
    }

    suspend fun getTransactionsByType(type: String): List<TransactionEntity> {
        return transactionDao.getTransactionsByType(type)
    }

    suspend fun getTransactionsByCategory(category: String): List<TransactionEntity> {
        return transactionDao.getTransactionsByCategory(category)
    }
}