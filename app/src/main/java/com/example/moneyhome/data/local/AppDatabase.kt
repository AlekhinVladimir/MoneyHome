package com.example.moneyhome.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneyhome.data.local.dao.ExpenseDao
import com.example.moneyhome.data.local.dao.IncomeDao
import com.example.moneyhome.data.local.entity.Expense
import com.example.moneyhome.data.local.entity.Income

@Database(entities = [Expense::class, Income::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao
}