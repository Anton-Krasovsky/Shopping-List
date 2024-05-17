package by.tigertosh.shoppinglist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.tigertosh.shoppinglist.databinding.ActivityShopListBinding

class ShopListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}