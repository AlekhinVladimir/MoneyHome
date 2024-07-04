package com.example.moneyhome.data.repositiry

import androidx.room.Transaction
import com.example.moneyhome.data.local.dao.TransactionDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }

    suspend fun getTransactionsByDateRange(startDate: String, endDate: String): List<Transaction> {
        return transactionDao.getTransactionsByDateRange(startDate, endDate)
    }

    suspend fun getTransactionsByType(type: String): List<Transaction> {
        return transactionDao.getTransactionsByType(type)
    }

    suspend fun getTransactionsByCategory(category: String): List<Transaction> {
        return transactionDao.getTransactionsByCategory(category)
    }
}