package com.example.noteapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.noteapp.entity.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM Note")
    suspend fun getAllNotes(): List<Note>

    @Delete
    suspend fun delete(note: Note)
}