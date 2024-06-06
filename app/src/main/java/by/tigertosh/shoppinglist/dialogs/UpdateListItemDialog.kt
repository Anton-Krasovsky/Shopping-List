package by.tigertosh.shoppinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.UpdateListItemDialogBinding
import by.tigertosh.shoppinglist.entities.ShoppingListItem


object UpdateListItemDialog {
    fun showDialog(context: Context, item: ShoppingListItem, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = UpdateListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            eTextUpdateItem.setText(item.name)
            eTextUpdateInfo.setText(item.itemInfo)
            buttonUpdateListTitle.setOnClickListener {
                if (eTextUpdateItem.text.toString().isNotEmpty()) {
                    val itemInfo = eTextUpdateInfo.text.toString().ifEmpty { null }
                    listener.onclick(item.copy(name = eTextUpdateItem.text.toString(),
                        itemInfo = itemInfo))
                }
                dialog?.dismiss()
            }


            }


        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onclick(item: ShoppingListItem)
    }
}