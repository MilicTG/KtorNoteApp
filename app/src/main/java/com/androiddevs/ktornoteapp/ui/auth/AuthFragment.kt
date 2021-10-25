package com.androiddevs.ktornoteapp.ui.auth

import android.content.SharedPreferences
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.data.remote.BasicAuthInterceptor
import com.androiddevs.ktornoteapp.databinding.FragmentAuthBinding
import com.androiddevs.ktornoteapp.other.Constants.KEY_LOGGED_IN_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.KEY_PASSWORD
import com.androiddevs.ktornoteapp.other.Constants.NO_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.NO_PASSWORD
import com.androiddevs.ktornoteapp.other.Status
import com.androiddevs.ktornoteapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : BaseFragment() {

    private lateinit var binding: FragmentAuthBinding

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var basicAuthInterceptor: BasicAuthInterceptor

    private var currentEmail: String? = null
    private var currentPassword: String? = null

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

        if (isLoggedIn()) {
            authenticateApi(
                email = currentEmail ?: "",
                password = currentPassword ?: ""
            )
            redirectLogin()
        }

        requireActivity().requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        subscribeToObservers()

        binding.btnRegister.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val confirmedPassword = binding.etRegisterPasswordConfirm.text.toString()
            viewModel.register(
                email = email,
                password = password,
                repeatedPassword = confirmedPassword
            )
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()
            currentEmail = email
            currentPassword = password
            viewModel.login(
                email = email,
                password = password
            )
        }
    }

    private fun isLoggedIn(): Boolean {
        currentEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL
        currentPassword = sharedPref.getString(KEY_PASSWORD, NO_PASSWORD) ?: NO_PASSWORD

        return currentEmail != NO_EMAIL && currentPassword != NO_PASSWORD
    }

    private fun authenticateApi(email: String, password: String) {
        basicAuthInterceptor.email = email
        basicAuthInterceptor.password = password
    }

    private fun redirectLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.authFragment, true)
            .build()
        findNavController().navigate(
            AuthFragmentDirections.actionAuthFragmentToNotesFragment(),
            navOptions
        )
    }

    private fun subscribeToObservers() {

        viewModel.loginStatus.observe(
            viewLifecycleOwner, Observer { result ->
                result?.let {
                    when (result.status) {
                        Status.SUCCESS -> {
                            binding.loginProgressBar.visibility = View.GONE
                            showSnackbar(
                                text = result.data ?: "Successfully logged in"
                            )

                            sharedPref.edit().putString(
                                KEY_LOGGED_IN_EMAIL, currentEmail
                            ).apply()
                            sharedPref.edit().putString(
                                KEY_PASSWORD, currentPassword
                            ).apply()

                            authenticateApi(
                                email = currentEmail ?: "",
                                password = currentPassword ?: ""
                            )

                            redirectLogin()
                        }
                        Status.ERROR -> {
                            binding.loginProgressBar.visibility = View.GONE
                            showSnackbar(
                                text = result.message ?: "A unknown error accrued!"
                            )
                        }
                        Status.LOADING -> {
                            binding.loginProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        )


        viewModel.registerStatus.observe(
            viewLifecycleOwner, Observer { result ->
                result?.let {
                    when (result.status) {
                        Status.SUCCESS -> {
                            binding.registerProgressBar.visibility = View.GONE
                            showSnackbar(
                                text = result.data ?: "Successfully register an account!"
                            )
                        }
                        Status.ERROR -> {
                            binding.registerProgressBar.visibility = View.GONE
                            showSnackbar(
                                text = result.message ?: "A unknown error occurred!"
                            )
                        }
                        Status.LOADING -> {
                            binding.registerProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        )
    }
}