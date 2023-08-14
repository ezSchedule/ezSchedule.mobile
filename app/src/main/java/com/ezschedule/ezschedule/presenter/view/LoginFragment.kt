package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.ezschedule.ezschedule.databinding.FragmentLoginBinding
import com.ezschedule.ezschedule.presenter.utils.TokenManager
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.domain.data.TenantRequest
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModel<TenantViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupSharedPreferences()
        setupObservers()
        setupFieldEmail()
        setupFieldPassword()
        setupButtonLogin()
    }

    private fun setupSharedPreferences() {
        if (TokenManager(requireContext()).getToken() != null) {
            Log.i("TEST", "Direct to next page, cause I have the token")
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            loginSuccess.observe(viewLifecycleOwner) {
                showSnackBarMessage("Tenant ${it.name} has logged in successfully!")
                TokenManager(requireContext()).saveToken(it.tokenJWT)
            }
            error.observe(viewLifecycleOwner) {
                showSnackBarMessage(it ?: "error")
            }
            setErrorEmail.observe(viewLifecycleOwner) {
                binding.tilEmail.error = it
            }
            setStatusButtonLogin.observe(viewLifecycleOwner) {
                binding.btnLogin.isEnabled = it
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupFieldEmail() {
        binding.tietEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.changeEmail(text.toString())
        }
    }

    private fun setupFieldPassword() {
        binding.tietPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.changePassword(text.toString())
        }
    }

    private fun setupButtonLogin() {
        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.login(
                    TenantRequest(
                        email = tietEmail.text.toString(),
                        password = tietPassword.text.toString()
                    )
                )
            }
        }
    }
}