package com.example.moneyhome.data.local.dao

import androidx.room.*
import com.example.moneyhome.data.local.entity.Income
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(income: Income)

    @Delete
    suspend fun delete(income: Income)

    @Query("SELECT * FROM incomes ORDER BY date DESC")
    fun getAllIncomes(): Flow<List<Income>>
}