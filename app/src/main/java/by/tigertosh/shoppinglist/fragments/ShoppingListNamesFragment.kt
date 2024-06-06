package by.tigertosh.shoppinglist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.tigertosh.shoppinglist.activities.MainApp
import by.tigertosh.shoppinglist.activities.ShopListActivity
import by.tigertosh.shoppinglist.adapter.ListNameAdapter
import by.tigertosh.shoppinglist.adapter.ListenerListName

import by.tigertosh.shoppinglist.base.BaseFragment
import by.tigertosh.shoppinglist.base.BaseViewModel
import by.tigertosh.shoppinglist.base.BaseViewModelFactory
import by.tigertosh.shoppinglist.databinding.FragmentShoppingListNamesBinding
import by.tigertosh.shoppinglist.dialogs.DeleteShopListNameDialog
import by.tigertosh.shoppinglist.dialogs.NewListDialog
import by.tigertosh.shoppinglist.entities.ShoppingListName
import by.tigertosh.shoppinglist.utils.TimeManager

class ShoppingListNamesFragment : BaseFragment(), ListenerListName {

    private lateinit var binding: FragmentShoppingListNamesBinding
    private lateinit var adapter: ListNameAdapter
    private val viewModel: BaseViewModel by activityViewModels {
        BaseViewModelFactory(
            (context?.applicationContext as MainApp).database
        )
    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onclick(name: String) {
                val shoppingListName = ShoppingListName(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                viewModel.insertShopListName(shoppingListName)
            }

        }, "")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {
        rcViewListName.layoutManager = LinearLayoutManager(activity)
        adapter = ListNameAdapter(this@ShoppingListNamesFragment)
        rcViewListName.adapter = adapter
    }

    private fun observer() {
        viewModel.allShopListNames.observe(viewLifecycleOwner) { shopList ->
            adapter.submitList(shopList)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListNamesFragment()
    }

    override fun onClickItem(name: ShoppingListName) {
        val intent = Intent(activity, ShopListActivity::class.java).apply {
            putExtra(ShopListActivity.SHOP_LIST_NAME, name)
        }
        startActivity(intent)
    }

    override fun deleteItem(id: Int) {
        DeleteShopListNameDialog.showDeleteDialog(
            context as AppCompatActivity,
            object : DeleteShopListNameDialog.Listener {
                override fun onclick() {
                    viewModel.deleteShoppingList(id, true)
                }

            })
    }

    override fun editItem(listName: ShoppingListName) {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onclick(name: String) {
                viewModel.updateShoppingListName(listName.copy(listName = name))
            }

        }, listName.listName)

    }


}