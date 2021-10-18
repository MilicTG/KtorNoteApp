package com.androiddevs.ktornoteapp.ui.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androiddevs.ktornoteapp.databinding.FragmentNoteDetailBinding
import com.androiddevs.ktornoteapp.ui.BaseFragment

class NoteDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}