package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sptech.user.databinding.FragmentScheduleUserBinding

class ScheduleUserFragment : Fragment() {
    private lateinit var binding: FragmentScheduleUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleUserBinding.inflate(inflater, container, false)
        return binding.root
    }
}