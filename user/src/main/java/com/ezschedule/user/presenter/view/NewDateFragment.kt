package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sptech.user.databinding.FragmentNewDateBinding

class NewDateFragment : Fragment() {
    private lateinit var binding: FragmentNewDateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickOnInfoScreen()
        setupClickOnPaymentScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    private fun setupClickOnInfoScreen() = with(binding.includeInfo) {
        btnBack.setOnClickListener {
            onDestroy()
        }
        btnNext.setOnClickListener {
            setupLayout(false)
        }
    }

    private fun setupClickOnPaymentScreen() = with(binding.includePayment) {
        btnCancel.setOnClickListener {
            setupLayout(true)
        }
        ibClipboard.setOnClickListener {

        }
    }

    private fun setupLayout(isInfoScreen: Boolean) {
        binding.includeInfo.root.isVisible = isInfoScreen
        binding.includePayment.root.isVisible = isInfoScreen.not()
    }
}