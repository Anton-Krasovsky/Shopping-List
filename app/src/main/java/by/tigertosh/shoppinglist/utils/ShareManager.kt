package by.tigertosh.shoppinglist.utils

import android.content.Intent
import by.tigertosh.shoppinglist.entities.ShoppingListItem

object ShareManager {

    fun shareShopList(shopList: List<ShoppingListItem>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeShareText(shopList, listName))
        }
        return intent
    }


    private fun makeShareText(shopList: List<ShoppingListItem>, listName: String): String {
        val sBuilder = StringBuilder()
        var counter = 0
        sBuilder.append("<<$listName>>")
        sBuilder.append("\n")
        shopList.forEach {
            sBuilder.append("${counter++} - ${it.name} (${it.itemInfo})")
            sBuilder.append("\n")
        }
        return sBuilder.toString()
    }
}