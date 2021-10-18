package com.androiddevs.ktornoteapp.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    fun showSnackbar(text: String) {
        Snackbar.make(
            requireView(),
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }
}