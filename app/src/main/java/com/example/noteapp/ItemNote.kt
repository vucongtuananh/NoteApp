package com.example.noteapp

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.entity.Note

class ItemNote(private var notes : List<Note>, private var colors : List<Int> = emptyList<Int>(), private val onClickListener: (Note) -> Unit) : RecyclerView.Adapter<ItemNote.NoteViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.tvTitle.text = notes[position].title
        if(colors.isNotEmpty()) {
        holder.item.setColor(colors[position])
        }
        holder.itemView.setOnClickListener {
            onClickListener(notes[position])
        }
    }

    override fun getItemCount(): Int {

        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newListNotes : List<Note>, newColors: List<Int>){
        notes = newListNotes
        colors = newColors
        notifyDataSetChanged()

    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.title_item)
        val item = itemView.findViewById<View>(R.id.note_item).background as GradientDrawable
    }
}