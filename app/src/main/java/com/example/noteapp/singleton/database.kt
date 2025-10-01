package com.example.noteapp.singleton

import android.content.Context
import androidx.room.Room
import com.example.noteapp.localdb.AppDatabase

object DatabaseProvider {
    private var db: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if(db == null){
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "note-db"
            ).build()
        }
        return db!!;
    }
}