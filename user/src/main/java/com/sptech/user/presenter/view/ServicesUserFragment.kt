package com.sptech.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sptech.user.databinding.FragmentServicesUserBinding

class ServicesUserFragment : Fragment() {
    private lateinit var binding: FragmentServicesUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesUserBinding.inflate(inflater, container, false)
        return binding.root
    }
}