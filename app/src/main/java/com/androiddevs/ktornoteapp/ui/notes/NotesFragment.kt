package com.androiddevs.ktornoteapp.ui.notes

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.databinding.FragmentNotesBinding
import com.androiddevs.ktornoteapp.other.Constants.KEY_LOGGED_IN_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.KEY_PASSWORD
import com.androiddevs.ktornoteapp.other.Constants.NO_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.NO_PASSWORD
import com.androiddevs.ktornoteapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : BaseFragment() {

    private lateinit var binding: FragmentNotesBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(
                NotesFragmentDirections
                    .actionNotesFragmentToAddEditNoteFragment(id = "")
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miLogout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL, NO_EMAIL).apply()
        sharedPreferences.edit().putString(KEY_PASSWORD, NO_PASSWORD).apply()

        val navOtions = NavOptions.Builder()
            .setPopUpTo(R.id.notesFragment, true)
            .build()
        findNavController().navigate(
            NotesFragmentDirections.actionNotesFragmentToAuthFragment(),
            navOtions
        )
    }
}