package by.tigertosh.shoppinglist.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.tigertosh.shoppinglist.database.MainDataBase

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory(private val database: MainDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            return BaseViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }

}