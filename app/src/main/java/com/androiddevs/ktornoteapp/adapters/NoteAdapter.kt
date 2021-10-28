package com.androiddevs.ktornoteapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.adapters.NoteAdapter.*
import com.androiddevs.ktornoteapp.data.local.entitys.Note
import com.androiddevs.ktornoteapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    private val differ = AsyncListDiffer(this, diffCallback)

    var notes: List<Note>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(private val itemBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(note: Note) {
            itemBinding.tvTitle.text = note.title

            if (!note.isSynced) {
                itemBinding.ivSynced.setImageResource(R.drawable.ic_cross)
                itemBinding.tvSynced.text = "Not Synced"
            } else {
                itemBinding.ivSynced.setImageResource(R.drawable.ic_check)
                itemBinding.tvSynced.text = "Synced"
            }

            val dateFormat = SimpleDateFormat("dd.MM.yy, HH:mm", Locale.getDefault())
            val dateString = dateFormat.format(note.date)
            itemBinding.tvDate.text = dateString

            val drawable = ResourcesCompat.getDrawable(
                itemBinding.root.resources, R.drawable.circle_shape, null
            )
            drawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                val color = Color.parseColor("#${note.color}")
                DrawableCompat.setTint(wrappedDrawable, color)
                itemBinding.viewNoteColor.background = it
            }

            itemBinding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(note)
                }
            }

        }
    }

    fun setOnItemClickListener(onItemClick: (Note) -> Unit) {
        this.onItemClickListener = onItemClick
    }
}