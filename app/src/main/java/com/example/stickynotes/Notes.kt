package com.example.stickynotes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "note")
data class Notes @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0 ,
    val title : String,
    val subTitle : String,
    val time : Long = System.currentTimeMillis(),
    val createdDate : LocalDate = LocalDate.now()
)