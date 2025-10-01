package com.example.noteapp

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ItemNote(private var notes : List<String>, private var colors : List<Int> = emptyList<Int>()) : RecyclerView.Adapter<ItemNote.NoteViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.tvTitle.text = notes[position]
        if(colors.isNotEmpty()) {
        holder.item.setColor(colors[position])
        }
    }

    override fun getItemCount(): Int {

        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTitle : List<String>, newColors: List<Int>){
        notes = newTitle
        colors = newColors
        notifyDataSetChanged()

    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.title_item)
        val item = itemView.findViewById<View>(R.id.note_item).background as GradientDrawable
    }
}