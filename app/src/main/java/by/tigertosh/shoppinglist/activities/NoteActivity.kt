package by.tigertosh.shoppinglist.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.ActivityNoteBinding
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.fragments.NoteFragment
import by.tigertosh.shoppinglist.utils.HtmlManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater).also { setContentView(it.root) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getNote()

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
        } else if (item.itemId == R.id.bold_note) {
            setBoldForSelectedText()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setNoteResult() {
        val stateNote: String
        val tempNote: NoteItem? = if (note == null) {
            stateNote = "new"
            createNewNote()
        } else {
            stateNote = "update"
            updateNote()
        }
        val intent = Intent().apply {
            putExtra(NoteFragment.NOTE_KEY, tempNote)
            putExtra(NoteFragment.NOTE_STATE, stateNote)
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
        HtmlManager.toHtml(binding.textNoteDescription.text),
        getCurrentTime(),
        ""
    )

    private fun getNote() {
        val sNote = intent.getSerializableExtra(NoteFragment.NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() = with(binding) {
        if (note != null) {
            textTitle.setText(note?.title)
            textNoteDescription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
        }
    }

    private fun updateNote() = with(binding) {
        note?.copy(
            title = textTitle.text.toString(),
            content = HtmlManager.toHtml(textNoteDescription.text)
        )
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPosition = textNoteDescription.selectionStart
        val endPosition = textNoteDescription.selectionEnd
        val styles = textNoteDescription.text
            .getSpans(startPosition, endPosition, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()) {
            textNoteDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        textNoteDescription.text
            .setSpan(boldStyle, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textNoteDescription.text.trim()
        textNoteDescription.setSelection(startPosition)
    }

}