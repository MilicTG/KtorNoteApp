package com.androiddevs.ktornoteapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.databinding.FragmentAuthBinding
import com.androiddevs.ktornoteapp.ui.BaseFragment

class AuthFragment : BaseFragment() {

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToNotesFragment()
            )
        }
    }

}