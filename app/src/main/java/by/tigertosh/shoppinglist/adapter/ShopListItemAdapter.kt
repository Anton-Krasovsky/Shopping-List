package by.tigertosh.shoppinglist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.LibraryListItemBinding
import by.tigertosh.shoppinglist.databinding.ShopListItemBinding
import by.tigertosh.shoppinglist.entities.ShoppingListItem


interface ListenerListItem {
    fun onClickItem(item: ShoppingListItem, state: Int)

}

class ShopListItemAdapter(private val listener: ListenerListItem) :
    ListAdapter<ShoppingListItem, ShopListItemAdapter.HolderListItem>(Comparator()) {

    class HolderListItem(val view: View) : RecyclerView.ViewHolder(view) {

        fun setItemData(item: ShoppingListItem, listener: ListenerListItem) {
            val binding = ShopListItemBinding.bind(view)
            binding.apply {
                textItemList.text = item.name
                textItemInfoList.text = item.itemInfo
                textItemInfoList.visibility = infoVisibility(item)
                checkBoxItem.isChecked = item.itemChecked
                setPaintFlag(binding)
                checkBoxItem.setOnClickListener {
                    listener.onClickItem(item.copy(itemChecked = checkBoxItem.isChecked), CHECK_BOX)
                }
                buttonEditItemLibrary.setOnClickListener {
                    listener.onClickItem(item, EDIT)
                }
            }
        }

        private fun infoVisibility(item: ShoppingListItem): Int {
            return if (item.itemInfo.isNullOrEmpty()) View.GONE
            else View.VISIBLE
        }

        private fun setPaintFlag(binding: ShopListItemBinding) {
            binding.apply {
                if (checkBoxItem.isChecked) {
                    textItemList.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    textItemInfoList.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    textItemList.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.grey_CP
                        )
                    )
                    textItemInfoList.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.grey_CP
                        )
                    )
                } else {
                    textItemList.paintFlags = Paint.ANTI_ALIAS_FLAG
                    textItemInfoList.paintFlags = Paint.ANTI_ALIAS_FLAG
                    textItemList.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.black
                        )
                    )
                    textItemInfoList.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.black
                        )
                    )
                }
            }
        }

        fun setLibraryData(item: ShoppingListItem, listener: ListenerListItem) {
            val binding = LibraryListItemBinding.bind(view)
            binding.apply {
                textItemList.text = item.name
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<ShoppingListItem>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ): Boolean = oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int = getItem(position).itemType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderListItem {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_list_item, parent, false)
        val viewLibrary = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_list_item, parent, false)
        return if (viewType == 0)
            HolderListItem(viewItem)
        else
            HolderListItem(viewLibrary)
    }

    override fun onBindViewHolder(holder: HolderListItem, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    companion object {
        const val EDIT = 0
        const val CHECK_BOX = 1
    }
}