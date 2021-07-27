package com.josias.soares.task.shared

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?):
            java.sql.Date {
        return java.sql.Date(value ?: 0)
    }

    @TypeConverter
    fun dateToTimestamp(date: java.sql.Date?)
            : Long {
        return date?.time ?: 0
    }
}