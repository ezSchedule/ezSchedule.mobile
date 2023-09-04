package com.ezschedule.ezschedule.presenter.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ezschedule.ezschedule.databinding.FragmentLoginBinding
import com.ezschedule.ezschedule.presenter.utils.TokenManager
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var activity: MainActivity
    private val viewModel by viewModel<TenantViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupActivity()
        setupFieldEmail()
        setupFieldPassword()
        setupButtonLogin()
    }

    private fun setupObservers() {
        with(viewModel) {
            loginSuccess.observe(viewLifecycleOwner) {
                savingInfoSharedPref(it)
                validateIsAdmin(it.isAdmin)
            }
            setLocationAndMenu.observe(viewLifecycleOwner) {
                findNavController().navigate(it.first)
                activity.setupBottomNavigation(it.second)
                activity.displayLoginItems(true)
            }
            error.observe(viewLifecycleOwner) {
                showSnackBarMessage(it ?: "error")
                setupLoading(false)
            }
            setErrorEmail.observe(viewLifecycleOwner) {
                binding.tilEmail.error = it
            }
            setStatusButtonLogin.observe(viewLifecycleOwner) {
                binding.btnLogin.isEnabled = it
            }
        }
    }

    private fun setupActivity() {
        activity = requireActivity() as MainActivity
    }

    private fun savingInfoSharedPref(it: TenantPresentation) {
        TokenManager(requireContext()).saveInfo(
            tenant = TenantPresentation(
                id = it.id,
                email = it.email,
                name = it.name,
                image = it.image,
                isAdmin = it.isAdmin,
                tokenJWT = it.tokenJWT,
                idCondominium = it.idCondominium
            )
        )
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
                setupLoading(true)
                viewModel.login(
                    TenantRequest(
                        email = tietEmail.text.toString(),
                        password = tietPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun setupLoading(isVisible: Boolean) {
        binding.includeLoading.root.isVisible = isVisible
        view?.let {
            requireContext().hideKeyboard(it)
        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}