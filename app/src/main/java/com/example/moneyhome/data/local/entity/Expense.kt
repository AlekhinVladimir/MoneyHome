package com.example.moneyhome.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val category: String,
    val amount: Double,
    val comment: String
)