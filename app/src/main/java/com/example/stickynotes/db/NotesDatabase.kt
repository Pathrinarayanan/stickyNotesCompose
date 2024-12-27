package com.example.stickynotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stickynotes.Notes

@Database(version = 1, entities = [Notes::class])
@TypeConverters(MyTypeConverters::class)
abstract class NotesDatabase : RoomDatabase(){
    abstract fun getDao(): Dao

    companion object{
        val title = "myNotes"
    }
}