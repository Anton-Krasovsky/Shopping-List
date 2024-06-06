package by.tigertosh.shoppinglist.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.adapter.ListenerListItem
import by.tigertosh.shoppinglist.adapter.ShopListItemAdapter
import by.tigertosh.shoppinglist.base.BaseViewModel
import by.tigertosh.shoppinglist.base.BaseViewModelFactory
import by.tigertosh.shoppinglist.databinding.ActivityShopListBinding
import by.tigertosh.shoppinglist.dialogs.UpdateListItemDialog
import by.tigertosh.shoppinglist.entities.ShoppingListItem
import by.tigertosh.shoppinglist.entities.ShoppingListName
import by.tigertosh.shoppinglist.utils.ShareManager

class ShopListActivity : AppCompatActivity(), ListenerListItem {

    private lateinit var binding: ActivityShopListBinding
    private val viewModel: BaseViewModel by viewModels {
        BaseViewModelFactory((applicationContext as MainApp).database)
    }
    private var shopListName: ShoppingListName? = null
    private lateinit var saveItem: MenuItem
    private var editText: EditText? = null
    private lateinit var adapter: ShopListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater).also { setContentView(it.root) }
        init()
        initRView()
        listItemObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item_list)!!
        val newItem = menu.findItem(R.id.new_item_list)
        editText = newItem.actionView?.findViewById<EditText>(R.id.addNewShopListItem)!!
        saveItem.isVisible = false
        newItem.setOnActionExpandListener(expandActionView())
        return true
    }

    private fun addNewShopListItem() {
        if (editText?.text.toString().isEmpty()) return
        val item = ShoppingListItem(
            null,
            editText?.text.toString(),
            null,
            false,
            shopListName?.id!!,
            0
        )
        editText?.text?.clear()
        viewModel.insertShopListItem(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_item_list -> {
                addNewShopListItem()
            }
            R.id.delete_list -> {
                viewModel.deleteShoppingList(shopListName?.id!!, true)
                finish()
            }
            R.id.clear_list -> {
                viewModel.deleteShoppingList(shopListName?.id!!, false)

            }
            R.id.share_list -> {
                startActivity(Intent.createChooser(
                    ShareManager.shareShopList(adapter.currentList, shopListName?.listName!!),
                    "Поделиться с помощью"
                ))

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }

        }
    }

    private fun listItemObserver() {
        viewModel.getAllShopListItem(shopListName?.id!!).observe(this) {
            adapter.submitList(it)
            binding.textList.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun initRView() = with(binding) {
        rvListItem.layoutManager = LinearLayoutManager(this@ShopListActivity)
        adapter = ShopListItemAdapter(this@ShopListActivity)
        rvListItem.adapter = adapter
    }


    @Suppress("DEPRECATION")
    private fun init() {
        shopListName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(SHOP_LIST_NAME, ShoppingListName::class.java)
        } else {
            intent.getSerializableExtra(SHOP_LIST_NAME) as ShoppingListName
        }
    }

    companion object {
        const val SHOP_LIST_NAME = "shopping_list_name"
    }

    override fun onClickItem(item: ShoppingListItem, state: Int) {
        when(state) {
            ShopListItemAdapter.CHECK_BOX -> viewModel.updateListItem(item)
            ShopListItemAdapter.EDIT -> editListItem(item)
        }

    }

    private fun editListItem(item: ShoppingListItem) {
        UpdateListItemDialog.showDialog(this, item, object : UpdateListItemDialog.Listener{
            override fun onclick(item: ShoppingListItem) {
                viewModel.updateListItem(item)
            }

        })
    }




}