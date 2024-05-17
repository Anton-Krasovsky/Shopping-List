package by.tigertosh.shoppinglist.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import by.tigertosh.shoppinglist.database.MainDataBase
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.entities.ShoppingListName
import kotlinx.coroutines.launch

class BaseViewModel(database: MainDataBase) : ViewModel() {

    private val dao = database.dao
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    val allShopListNames: LiveData<List<ShoppingListName>> = dao.getAllShopListName().asLiveData()

    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
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


    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    fun deleteShoppingListName(id: Int) = viewModelScope.launch {
        dao.deleteShoppingListName(id)
    }


}