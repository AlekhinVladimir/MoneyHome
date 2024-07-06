package com.example.moneyhome.data.local

import androidx.room.TypeConverter
import java.util.Date

object DateConverter {
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(value: Long?): Date? {
        return if (value == null) {
            null
        } else {
            Date(value)
        }
    }
}