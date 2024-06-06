package by.tigertosh.shoppinglist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.entities.ShoppingListItem
import by.tigertosh.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM shopping_list_names")
    fun getAllShopListName(): Flow<List<ShoppingListName>>

    @Query("SELECT * FROM shop_list_item WHERE listId LIKE :listId")
    fun getAllShopListItem(listId: Int): Flow<List<ShoppingListItem>>

    @Query("DELETE FROM note_list WHERE id IS :noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("DELETE FROM shopping_list_names WHERE id IS :listNameId")
    suspend fun deleteShoppingListName(listNameId: Int)

    @Query("DELETE FROM shop_list_item WHERE listId LIKE :listId")
    suspend fun deleteShopItemByListId(listId: Int)

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertShopListItem(listItem: ShoppingListItem)

    @Insert
    suspend fun insertShopListName(name: ShoppingListName)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateShoppingListName(listName: ShoppingListName)

    @Update
    suspend fun updateListItem(item: ShoppingListItem)
}