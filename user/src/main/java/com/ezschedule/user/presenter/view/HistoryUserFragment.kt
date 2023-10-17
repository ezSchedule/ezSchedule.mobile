package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sptech.user.databinding.FragmentHistoryUserBinding

class HistoryUserFragment : Fragment() {
    private lateinit var binding: FragmentHistoryUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryUserBinding.inflate(inflater, container, false)
        return binding.root
    }
}