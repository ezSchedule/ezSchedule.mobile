package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ezschedule.ezschedule.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActivity()
        setupButtonClick()
    }

    private fun setupButtonClick() {
        binding.fragSettingsBtnBack.setOnClickListener {
            findNavController().popBackStack()
            activity.displayLoginItems(true)
        }
    }

    private fun setupActivity() {
        activity = requireActivity() as MainActivity
    }
}