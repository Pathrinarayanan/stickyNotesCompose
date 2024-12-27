package com.example.stickynotes

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stickynotes.db.NotesDatabase

class MainApplication :Application() {
    companion object {
        lateinit var mydb: NotesDatabase
    }
    override fun onCreate() {
        super.onCreate()
        mydb = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            NotesDatabase.title
        )
            .allowMainThreadQueries()
            .build()
    }
}