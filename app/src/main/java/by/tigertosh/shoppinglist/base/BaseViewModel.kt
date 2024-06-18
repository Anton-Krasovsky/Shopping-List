package by.tigertosh.shoppinglist.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import by.tigertosh.shoppinglist.database.MainDataBase
import by.tigertosh.shoppinglist.entities.LibraryItem
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.entities.ShoppingListItem
import by.tigertosh.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.launch

class BaseViewModel(database: MainDataBase) : ViewModel() {

    private val dao = database.dao
    val libraryItem = MutableLiveData<List<LibraryItem>>()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShoppingListName>> = dao.getAllShopListName().asLiveData()

    fun getAllShopListItem(id: Int): LiveData<List<ShoppingListItem>> =
        dao.getAllShopListItem(id).asLiveData()

    fun getAllLibraryItem(name: String) = viewModelScope.launch {
        libraryItem.postValue(dao.getAllLibraryItem(name))
    }


    private suspend fun isAllLibraryItemExists(name: String): Boolean =
        dao.getAllLibraryItem(name).isNotEmpty()


    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun insertShopListItem(listItem: ShoppingListItem) = viewModelScope.launch {
        dao.insertShopListItem(listItem)
        if (!isAllLibraryItemExists(listItem.name)) {
            dao.insertLibraryItem(LibraryItem(null, listItem.name))
        }
    }


    fun insertShopListName(name: ShoppingListName) = viewModelScope.launch {
        dao.insertShopListName(name)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    fun updateShoppingListName(listName: ShoppingListName) = viewModelScope.launch {
        dao.updateShoppingListName(listName)
    }

    fun updateListItem(item: ShoppingListItem) = viewModelScope.launch {
        dao.updateListItem(item)
    }


    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteShoppingList(id: Int, deleteList: Boolean) = viewModelScope.launch {
        if (deleteList) dao.deleteShoppingListName(id)
        dao.deleteShopItemByListId(id)
    }


}