package by.tigertosh.shoppinglist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.ListNameItemBinding
import by.tigertosh.shoppinglist.entities.ShoppingListName


interface ListenerListName {

    fun onClickItem(name: ShoppingListName)
    fun deleteItem(id: Int)
    fun editItem(listName: ShoppingListName)
}

class ListNameAdapter(private val listener: ListenerListName) :
    ListAdapter<ShoppingListName, ListNameAdapter.ListNameHolder>(Comparator()) {

    class ListNameHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)
        fun setData(listName: ShoppingListName, listener: ListenerListName) = with(binding) {
            titleListName.text = listName.listName
            timeListName.text = listName.time
            itemView.setOnClickListener {
                listener.onClickItem(listName)
            }
            deleteListName.setOnClickListener {
                listener.deleteItem(listName.id!!)
            }

            editListName.setOnClickListener {
                listener.editItem(listName)
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<ShoppingListName>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListName,
            newItem: ShoppingListName
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingListName,
            newItem: ShoppingListName
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNameHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_name_item, parent, false)
        return ListNameHolder(view)
    }

    override fun onBindViewHolder(holder: ListNameHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }
}