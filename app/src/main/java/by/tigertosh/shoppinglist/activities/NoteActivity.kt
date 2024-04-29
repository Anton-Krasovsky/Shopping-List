package by.tigertosh.shoppinglist.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.ActivityNoteBinding
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding = ActivityNoteBinding.inflate(layoutInflater).also { setContentView(it.root) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_note) {
            setNoteResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNoteResult() {
        val intent = Intent().apply {
            putExtra(NoteFragment.NOTE_KEY, createNewNote())
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
    private fun createNewNote(): NoteItem = NoteItem(
        null,
        binding.textTitle.text.toString(),
        binding.textNoteDescription.text.toString(),
        getCurrentTime(),
        ""
    )

}