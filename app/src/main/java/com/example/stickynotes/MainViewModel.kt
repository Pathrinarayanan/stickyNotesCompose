package com.example.stickynotes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val notesDao = MainApplication.mydb.getDao()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addNotes(title : String, subTitle : String ){

            notesDao.addNotes(Notes(title = title, subTitle = subTitle))

    }


    fun getNotes(): LiveData<List<Notes>> {
        return notesDao.getNotes()
    }
    fun deleteNotes(id : Int){
        return notesDao.deleteNotes(id)
    }
    fun updateNotes(title : String, subtitle: String, id : Int){
        return notesDao.updateNotes(title, subtitle , id)
    }

}