package by.tigertosh.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("itemInfo")
    val itemInfo: String?,
    @ColumnInfo("itemChecked")
    val itemChecked: Boolean = false,
    @ColumnInfo("listId")
    val listId: Int,
    @ColumnInfo("itemType")
    val itemType: Int = 0,


    )
