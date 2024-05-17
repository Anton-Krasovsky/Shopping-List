package by.tigertosh.shoppinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.NewListDialogBinding


object NewListDialog {
    fun showDialog(context: Context, listener: Listener, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            listName.setText(name)
            if (name.isNotEmpty()) {
                buttonCreate.text = context.getString(R.string.update_list)
                newList.text = context.getString(R.string.update_title_list)
            }
            buttonCreate.setOnClickListener {
                val newList = listName.text.toString()
                if (newList.isNotEmpty()) {
                    listener.onclick(newList)
                }
                dialog?.dismiss()
            }

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onclick(name: String)
    }
}