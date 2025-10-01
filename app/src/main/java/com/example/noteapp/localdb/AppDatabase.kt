package com.example.noteapp.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.dao.NoteDao
import com.example.noteapp.entity.Note

@Database(
    entities = [Note::class], // 👈 liệt kê entity ở đây
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}