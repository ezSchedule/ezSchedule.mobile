package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentHistoryBinding
import com.ezschedule.admin.presenter.adapter.HistoryAdapter
import com.ezschedule.admin.presenter.adapter.ServicesAdapter
import com.ezschedule.network.domain.presentation.HistoryPresentation

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragRvHistory.adapter = HistoryAdapter(getHistoryData())
    }
    private fun getHistoryData() = listOf<HistoryPresentation>(
        HistoryPresentation("Kleber Santana","Magnolia",110.0),
        HistoryPresentation("Mércio Paludo","Magnolia",110.0,false),
        HistoryPresentation("Rogério Parce","King Vamp",300.0,false),
        HistoryPresentation("Cido Caroline","Porshe",1200.0),
        HistoryPresentation("Dante Tobias","Vampire",900.0),
    )
}