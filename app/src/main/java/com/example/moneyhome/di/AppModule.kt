package com.example.moneyhome.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moneyhome.data.local.AppDatabase
import com.example.moneyhome.data.local.dao.TransactionDao
import com.example.moneyhome.data.repositiry.TransactionRepository
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
        ).allowMainThreadQueries()// Только для разработки
            .build()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao {
        return appDatabase.transactionDao()
    }


    @Provides
    fun provideTransactionRepository(transactionDao: TransactionDao ): TransactionRepository {
        return TransactionRepository(transactionDao)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application
    }
}