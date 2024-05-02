package by.tigertosh.shoppinglist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.tigertosh.shoppinglist.R
import by.tigertosh.shoppinglist.databinding.ActivityNoteBinding
import by.tigertosh.shoppinglist.entities.NoteItem
import by.tigertosh.shoppinglist.fragments.NoteFragment
import by.tigertosh.shoppinglist.utils.HtmlManager
import by.tigertosh.shoppinglist.utils.MyTouchListener
import java.io.Serializable
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
        moveColorPicker()
        onClickColorListener()

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
        } else if (item.itemId == R.id.colorPicker_note) {
            if (binding.colorPicker.isShown) {
                closeColorPicker()
            } else {
                openColorPicker()
            }
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

    @Suppress("DEPRECATION")
    private fun getNote() {
        val sNote1: Serializable?
        var sNote2: Serializable? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            sNote1 = intent.getSerializableExtra(NoteFragment.NOTE_KEY, NoteItem::class.java)
            if (sNote1 != null) {
                note = sNote1
                fillNote()
            }
        } else {
            sNote2 = intent.getSerializableExtra(NoteFragment.NOTE_KEY)
        }
        if (sNote2 != null) {
            note = sNote2 as NoteItem
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

    private fun setColorForSelectedText(colorId: Int) = with(binding) {
        val startPosition = textNoteDescription.selectionStart
        val endPosition = textNoteDescription.selectionEnd
        val styles = textNoteDescription.text
            .getSpans(startPosition, endPosition, ForegroundColorSpan::class.java)

        if (styles.isNotEmpty()) textNoteDescription.text.removeSpan(styles[0])

        textNoteDescription.text
            .setSpan(ForegroundColorSpan(ContextCompat.getColor(this@NoteActivity, colorId)),
                startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textNoteDescription.text.trim()
        textNoteDescription.setSelection(startPosition)
    }

    private fun onClickColorListener() = with(binding) {
        redCP.setOnClickListener {
            setColorForSelectedText(R.color.red_CP)
        }
        blueCP.setOnClickListener {
            setColorForSelectedText(R.color.blue_CP)
        }
        greenCP.setOnClickListener {
            setColorForSelectedText(R.color.green_CP)
        }
        greyCP.setOnClickListener {
            setColorForSelectedText(R.color.grey_CP)
        }
        yellowCP.setOnClickListener {
            setColorForSelectedText(R.color.yellow_CP)
        }
        orangeCP.setOnClickListener {
            setColorForSelectedText(R.color.orange_CP)
        }
    }

    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        val closeAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        closeAnim.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
        binding.colorPicker.startAnimation(closeAnim)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun moveColorPicker() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }




}