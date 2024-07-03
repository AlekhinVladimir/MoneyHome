package com.example.moneyhome.di

import android.content.Context
import androidx.room.Room
import com.example.moneyhome.data.local.AppDatabase
import com.example.moneyhome.data.local.dao.ExpenseDao
import com.example.moneyhome.data.local.dao.IncomeDao
import com.example.moneyhome.data.repositiry.ExpenseRepository
import com.example.moneyhome.data.repositiry.IncomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "expense_tracker_db"
        ).build()
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }

    @Provides
    fun provideIncomeDao(appDatabase: AppDatabase): IncomeDao {
        return appDatabase.incomeDao()
    }

    @Provides
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpenseRepository {
        return ExpenseRepository(expenseDao)
    }

    @Provides
    fun provideIncomeRepository(incomeDao: IncomeDao): IncomeRepository {
        return IncomeRepository(incomeDao)
    }
}