package com.example.moneyhome.data.repositiry

import android.util.Log
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

    suspend fun deleteTransaction(transaction: Int) {
        transactionDao.delete(transaction)
    }

    suspend fun getAllTransactions(): List<TransactionEntity> {
        val transactions = transactionDao.getAllTransactions()
        Log.d("TransactionRepository", "Fetched ${transactions.size} transactions from database")
        return transactions
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
    suspend fun getTransactionsByCategoryAndType(category: String, type: String): List<TransactionEntity> {
        return transactionDao.getTransactionsByCategoryAndType(category, type)
    }
}