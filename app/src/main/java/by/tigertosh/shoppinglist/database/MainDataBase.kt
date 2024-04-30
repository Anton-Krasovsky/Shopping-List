package by.tigertosh.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.tigertosh.shoppinglist.database.dao.Dao
import by.tigertosh.shoppinglist.entities.LibraryItem
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.entities.ShoppingListItem
import by.tigertosh.shoppinglist.entities.ShoppingListName

@Database(
    entities = [ShoppingListName::class,
        ShoppingListItem::class,
        NoteItem::class,
        LibraryItem::class],
    version = 1
)
abstract class MainDataBase : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}