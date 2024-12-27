package com.example.stickynotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.stickynotes.Notes

@Dao
interface Dao {

    @Query("Select * from note")
    fun getNotes() : LiveData<List<Notes>>

    @Insert
    fun addNotes(notes : Notes)

    @Query("Update note set title = :title , subtitle  = :subTitle where id = :id")
    fun updateNotes(title:String, subTitle :String, id : Int)

    @Query("delete from note where id = :id")
    fun deleteNotes(id: Int)


}