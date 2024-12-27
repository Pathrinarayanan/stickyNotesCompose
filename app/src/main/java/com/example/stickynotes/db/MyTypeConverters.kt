package com.example.stickynotes.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate


class MyTypeConverters {
    @TypeConverter
    fun fromLocalDate(date : LocalDate) : String{
        return date.toString()
    }
    @TypeConverter
    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalDate(date: String): LocalDate{
        return LocalDate.parse(date)
    }
}