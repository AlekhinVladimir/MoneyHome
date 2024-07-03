package com.example.moneyhome.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val category: String,
    val amount: Double,
    val comment: String
)