package com.example.moneyhome.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val type: String,
    val category: String,
    val amount: Double,
    val comment: String
)
