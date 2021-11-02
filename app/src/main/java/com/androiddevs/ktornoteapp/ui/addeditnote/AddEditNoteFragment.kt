package com.androiddevs.ktornoteapp.ui.addeditnote

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.data.local.entitys.Note
import com.androiddevs.ktornoteapp.databinding.FragmentAddEditNoteBinding
import com.androiddevs.ktornoteapp.other.Constants.DEFAULT_NOTE_COLOR
import com.androiddevs.ktornoteapp.other.Constants.KEY_LOGGED_IN_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.NO_EMAIL
import com.androiddevs.ktornoteapp.other.Status
import com.androiddevs.ktornoteapp.ui.BaseFragment
import com.androiddevs.ktornoteapp.ui.dialogs.ColorPickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import javax.inject.Inject

const val FRAGMENT_TAG = "AddEditNoteFragment"

@DelicateCoroutinesApi
@AndroidEntryPoint
class AddEditNoteFragment : BaseFragment() {

    private lateinit var binding: FragmentAddEditNoteBinding

    private val viewModel: AddEditNoteViewModel by viewModels()

    private val args: AddEditNoteFragmentArgs by navArgs()

    @Inject
    lateinit var sharedPref: SharedPreferences

    private var curNote: Note? = null
    private var curNoteColor = DEFAULT_NOTE_COLOR

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id.isNotEmpty()) {
            viewModel.getNoteById(args.id)
            subscribeToObservers()
        }

        if (savedInstanceState != null) {
            val colorPickerDialog = parentFragmentManager.findFragmentByTag(FRAGMENT_TAG)
                    as ColorPickerDialogFragment?
            colorPickerDialog?.setPositiveListener {
                changeViewNoteColor(it)
            }
        }

        binding.viewNoteColor.setOnClickListener {
            ColorPickerDialogFragment().apply {
                setPositiveListener {
                    changeViewNoteColor(it)
                }
            }.show(parentFragmentManager, FRAGMENT_TAG)
        }
    }

    private fun changeViewNoteColor(colorString: String) {
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.circle_shape, null)
        drawable?.let {
            val wrappedDrawable = DrawableCompat.wrap(it)
            val color = Color.parseColor("#$colorString")
            DrawableCompat.setTint(wrappedDrawable, color)
            binding.viewNoteColor.background = wrappedDrawable
            curNoteColor = colorString
        }
    }

    private fun subscribeToObservers() {
        viewModel.note.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        val note = result.data!!
                        curNote = note
                        binding.etNoteTitle.setText(note.title)
                        binding.etNoteContent.setText(note.content)
                        changeViewNoteColor(note.color)
                    }
                    Status.ERROR -> {
                        showSnackbar(result.message ?: "Note not found")
                    }
                    Status.LOADING -> {
                        /* NO-OP */
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val authEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL

        val title = binding.etNoteTitle.text.toString()
        val content = binding.etNoteContent.text.toString()
        if (title.isEmpty() || content.isEmpty()) {
            return
        }
        val date = System.currentTimeMillis()
        val color = curNoteColor
        val id = curNote?.id ?: UUID.randomUUID().toString()
        val owners = curNote?.owners ?: listOf(authEmail)
        val note = Note(title, content, date, owners, color, id = id)
        viewModel.insertNote(note)
    }
}