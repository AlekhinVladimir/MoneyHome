package com.example.moneyhome.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getTransactionsByDateRange(startDate: String, endDate: String): List<Transaction>

    @Query("SELECT * FROM transactions WHERE type = :type")
    suspend fun getTransactionsByType(type: String): List<Transaction>

    @Query("SELECT * FROM transactions WHERE category = :category")
    suspend fun getTransactionsByCategory(category: String): List<Transaction>