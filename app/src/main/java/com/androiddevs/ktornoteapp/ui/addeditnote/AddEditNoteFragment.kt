package com.androiddevs.ktornoteapp.ui.addeditnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androiddevs.ktornoteapp.databinding.FragmentAddEditNoteBinding
import com.androiddevs.ktornoteapp.ui.BaseFragment

class AddEditNoteFragment : BaseFragment() {

    private lateinit var binding: FragmentAddEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
}