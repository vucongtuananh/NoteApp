package com.example.noteapp

import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.localdb.AppDatabase
import com.example.noteapp.singleton.DatabaseProvider
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
        var adapter = ItemNote(emptyList(),emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listNoteItem = findViewById<RecyclerView>(R.id.listNoteItem)
        listNoteItem.adapter = adapter
        listNoteItem.layoutManager = LinearLayoutManager(this)
        val db = DatabaseProvider.getDatabase(this)
        val noteDao = db.noteDao()
        lifecycleScope.launch {
            val notes =noteDao.getAllNotes()
            if(notes.isEmpty()){
                findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.GONE
                findViewById<TextView>(R.id.emptyList).visibility = View.VISIBLE
            }else {
                findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.VISIBLE
                findViewById<TextView>(R.id.emptyList).visibility = View.GONE
            val listColor = generateColorList(notes.size)
            val listTitle = notes.map { it.title }
            adapter.updateData(newTitle = listTitle, newColors = listColor)
            }
        }




        val addNoteBtn = findViewById<Button>(R.id.addNoteBtn)
        addNoteBtn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent,1)
        }


    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            val db = DatabaseProvider.getDatabase(this)
            val noteDao = db.noteDao()
            lifecycleScope.launch {
                val notes =noteDao.getAllNotes()
                if(notes.isEmpty()){
                    findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.GONE
                    findViewById<TextView>(R.id.emptyList).visibility = View.VISIBLE
                }else {
                    findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.emptyList).visibility = View.GONE
                    val listColor = generateColorList(notes.size)
                    val listTitle = notes.map { it.title }
                    adapter.updateData(newTitle = listTitle, newColors = listColor)
                }
            }
        }
    }

    fun getColorForIndex(index: Int): Int {
        val random = Random(index.toLong()) // seed by index
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)
        return Color.rgb(red, green, blue)
    }
    fun generateColorList(size :Int) : List<Int> {
        return List(size) {index -> getColorForIndex(index)}
    }
}