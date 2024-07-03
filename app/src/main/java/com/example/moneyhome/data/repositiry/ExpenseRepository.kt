package com.example.moneyhome.data.repositiry

import com.example.moneyhome.data.local.dao.ExpenseDao
import com.example.moneyhome.data.local.entity.Expense
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(private val expenseDao: ExpenseDao) {
    suspend fun insert(expense: Expense) = expenseDao.insert(expense)
    suspend fun delete(expense: Expense) = expenseDao.delete(expense)
    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
}