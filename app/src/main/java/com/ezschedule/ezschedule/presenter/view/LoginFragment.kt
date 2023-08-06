package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ezschedule.ezschedule.data.utils.TokenManager
import com.ezschedule.ezschedule.databinding.FragmentLoginBinding
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.domain.data.TenantLoginRequest
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

        if (TokenManager(requireContext()).getToken() != null) {
            Log.i("TEST", "Direct to next page, cause I have the token")
        }
        viewModel.login(TenantLoginRequest("elias@gmail.com", "12345678"))
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            tenant.observe(viewLifecycleOwner) {
                Toast.makeText(
                    requireContext(),
                    "Tenant ${it.name} has logged in successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                TokenManager(requireContext()).saveToken(it.tokenJWT)
            }
            error.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}