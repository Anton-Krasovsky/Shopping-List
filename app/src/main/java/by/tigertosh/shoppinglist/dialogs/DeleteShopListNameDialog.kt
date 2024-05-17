package by.tigertosh.shoppinglist.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import by.tigertosh.shoppinglist.databinding.DeleteDialogBinding


object DeleteShopListNameDialog {
    fun showDeleteDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            deleteYesButton.setOnClickListener {
                listener.onclick()
                dialog?.dismiss()
            }
            deleteNoButton.setOnClickListener {
                dialog?.dismiss()
            }


        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onclick()
    }


}