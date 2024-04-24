package by.tigertosh.shoppinglist.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        setOnClickBNV()

    }


    private fun setOnClickBNV() {
        binding.bnv.setBackgroundColor(ContextCompat.getColor(applicationContext, android.R.color.transparent))
        binding.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings -> {
                   toast("You open settings")
                }
                R.id.notes -> {
                    toast("You open notes")
                }
                R.id.shop_list-> {
                    toast("You open shop list")
                }
                R.id.new_item -> {
                    toast("You open new item")
                }
            }
            true
        }
    }

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}