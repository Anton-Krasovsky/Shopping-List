package by.tigertosh.shoppinglist.activities

import android.app.Application
import by.tigertosh.shoppinglist.database.MainDataBase

class MainApp : Application() {

    val database by lazy { MainDataBase.getDataBase(this) }
}