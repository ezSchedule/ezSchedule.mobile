package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }
}