package com.androiddevs.ktornoteapp.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.databinding.FragmentNotesBinding
import com.androiddevs.ktornoteapp.ui.BaseFragment

class NotesFragment : BaseFragment() {

    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
}