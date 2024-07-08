package com.example.moneyhome.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneyhome.data.local.entity.TransactionEntity
import java.util.Date

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getTransactionsByDateRange(startDate: Date, endDate: Date): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE type = :type")
    suspend fun getTransactionsByType(type: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE category = :category")
    suspend fun getTransactionsByCategory(category: String): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE category = :category AND type = :type")
    suspend fun getTransactionsByCategoryAndType(category: String, type: String): List<TransactionEntity>
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDateObj AND :endDateObj AND category = :category")
    suspend fun getTransactionsByDateRangeandCategory(startDateObj: Date, endDateObj: Date, category: String): List<TransactionEntity>?
}