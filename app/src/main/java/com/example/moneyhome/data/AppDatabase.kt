package com.example.moneyhome.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneyhome.data.dao.TransactionDao
import com.example.moneyhome.data.local.DateConverter
import com.example.moneyhome.domain.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}