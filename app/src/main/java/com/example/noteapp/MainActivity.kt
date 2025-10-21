package com.example.noteapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.singleton.DatabaseProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var adapter = ItemNote(emptyList(), emptyList())
    lateinit var flutterEngine: FlutterEngine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)

        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
            .getInstance()
            .put("my_engine_id", flutterEngine)

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
            val notes = noteDao.getAllNotes()
            if (notes.isEmpty()) {
                findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.GONE
                findViewById<TextView>(R.id.emptyList).visibility = View.VISIBLE
            } else {
                findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.VISIBLE
                findViewById<TextView>(R.id.emptyList).visibility = View.GONE
                val listColor = generateColorList(notes.size)
                val listTitle = notes.map { it.title }
                adapter.updateData(newTitle = listTitle, newColors = listColor)
            }
        }

        val findBtn = findViewById<ImageButton>(R.id.findBtn)
        findBtn.setOnClickListener {
            startActivity(
                FlutterActivity
//                    .withCachedEngine("my_engine_id")
                    .withNewEngine()
                    .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                    .build(this)
            )
        }

        val addNoteBtn = findViewById<FloatingActionButton>(R.id.addNoteBtn)
        addNoteBtn.setOnClickListener {
//            val intent = Intent(this, AddNoteActivity::class.java)
//            startActivityForResult(intent,1)
            startActivity(
                FlutterActivity
//                    .withCachedEngine("my_engine_id")
                    .withNewEngine()
                    .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                    .build(this)
            )
        }


    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val db = DatabaseProvider.getDatabase(this)
            val noteDao = db.noteDao()
            lifecycleScope.launch {
                val notes = noteDao.getAllNotes()
                if (notes.isEmpty()) {
                    findViewById<RecyclerView>(R.id.listNoteItem).visibility = View.GONE
                    findViewById<TextView>(R.id.emptyList).visibility = View.VISIBLE
                } else {
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

    fun generateColorList(size: Int): List<Int> {
        return List(size) { index -> getColorForIndex(index) }
    }
}