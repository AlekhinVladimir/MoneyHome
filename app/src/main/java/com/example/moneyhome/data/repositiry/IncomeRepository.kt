package com.example.moneyhome.data.repositiry

import com.example.moneyhome.data.local.dao.IncomeDao
import com.example.moneyhome.data.local.entity.Income
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IncomeRepository @Inject constructor(private val incomeDao: IncomeDao) {
    suspend fun insert(income: Income) = incomeDao.insert(income)
    suspend fun delete(income: Income) = incomeDao.delete(income)
    fun getAllIncomes(): Flow<List<Income>> = incomeDao.getAllIncomes()
}