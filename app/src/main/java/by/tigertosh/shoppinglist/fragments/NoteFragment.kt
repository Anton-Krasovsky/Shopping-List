package by.tigertosh.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.tigertosh.shoppinglist.activities.MainApp
import by.tigertosh.shoppinglist.activities.NoteActivity
import by.tigertosh.shoppinglist.adapter.Listener
import by.tigertosh.shoppinglist.adapter.NoteAdapter
import by.tigertosh.shoppinglist.base.BaseFragment
import by.tigertosh.shoppinglist.base.BaseViewModel
import by.tigertosh.shoppinglist.base.BaseViewModelFactory
import by.tigertosh.shoppinglist.databinding.FragmentNoteBinding
import by.tigertosh.shoppinglist.entities.NoteItem

class NoteFragment : BaseFragment(), Listener {


    private lateinit var binding: FragmentNoteBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private val viewModel: BaseViewModel by activityViewModels {
        BaseViewModelFactory(
            (context?.applicationContext as MainApp).database
        )
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {
        adapter = NoteAdapter(this@NoteFragment)
        rcViewNote.layoutManager = LinearLayoutManager(activity)
        rcViewNote.adapter = adapter
    }

    private fun observer() {
        viewModel.allNotes.observe(viewLifecycleOwner) { noteList ->
            adapter.submitList(noteList)
        }
    }


    private fun onEditResult() {
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val noteState = it.data?.getStringExtra(NOTE_STATE)
                if (noteState == "update") {
                    viewModel.updateNote(it.data?.getSerializableExtra(NOTE_KEY) as NoteItem)
                } else {
                    viewModel.insertNote(it.data?.getSerializableExtra(NOTE_KEY) as NoteItem)
                }
            }
        }
    }

    companion object {

        const val NOTE_KEY = "note key"
        const val NOTE_STATE = "note state"

        @JvmStatic
        fun newInstance() = NoteFragment()

    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity, NoteActivity::class.java).apply {
            putExtra(NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }

    override fun deleteItem(id: Int) {
        viewModel.deleteNote(id)
    }
}