package by.tigertosh.shoppinglist.base

import androidx.appcompat.app.AppCompatActivity
import by.tigertosh.shoppinglist.R

object FragmentManager {
    //    Создаем переменную для того, чтобы знать какой fragment сейчас задействован (подключен к activity)
    var currentFragment: BaseFragment? = null

    //    Функция для переключения между  fragments
    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, newFragment)
            .commit()
        currentFragment = newFragment
    }
}