package com.example.trackprogress.Database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class Converters {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return dateString?.let { dateFormat.parse(it) }
    }
}