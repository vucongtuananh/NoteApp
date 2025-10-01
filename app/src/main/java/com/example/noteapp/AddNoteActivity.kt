package com.example.noteapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.noteapp.entity.Note
import com.example.noteapp.localdb.AppDatabase
import com.example.noteapp.singleton.DatabaseProvider
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addNote = findViewById<Button>(R.id.addNote)
        addNote.setOnClickListener {
            // add to local
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()
            val newNote : Note = Note(title = title, content = content)
            val dao = DatabaseProvider.getDatabase(this).noteDao()
            lifecycleScope.launch {
                dao.insert(newNote)
            }
            setResult(RESULT_OK)
            finish()
        }
    }
}