package by.tigertosh.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.NoteListItemBinding
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.utils.HtmlManager


interface Listener {

    fun onClickItem(note: NoteItem)
    fun deleteItem(id: Int)
}

class NoteAdapter(private val listener: Listener) :
    ListAdapter<NoteItem, NoteAdapter.ItemHolder>(Comparator()) {

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NoteListItemBinding.bind(view)
        fun setData(note: NoteItem, listener: Listener) = with(binding) {
            noteTitle.text = note.title
            noteDescription.text = HtmlManager.getFromHtml(note.content).trim()
            noteTime.text = note.time
            itemView.setOnClickListener {
                listener.onClickItem(note)
            }
            buttonDeleteNote.setOnClickListener {
                listener.deleteItem(note.id!!)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_list_item, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }
}