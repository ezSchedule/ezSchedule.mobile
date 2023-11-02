package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ezschedule.network.domain.presentation.SchedulesPresentation
import com.ezschedule.user.presenter.adapter.SchedulesAdapter
import com.sptech.user.databinding.FragmentScheduleUserBinding
import kotlin.math.abs

class ScheduleUserFragment : Fragment() {
    private lateinit var binding: FragmentScheduleUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.vpSchedules.apply {
            adapter = SchedulesAdapter(getData(), requireContext())
            offscreenPageLimit = 2
            clipChildren = false
            clipToPadding = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer { page, position ->
                    page.scaleY = 0.85f + (1 - abs(position)) * 0.14f
                }
            })
        }
    }

    private fun getData() = listOf(
        SchedulesPresentation(
            salon = "Salão Magnólia",
            event = "Aniversário Carol",
            date = "Segunda, 13 de Março",
            peoples = 120
        ),
        SchedulesPresentation(
            salon = "Salão Magnólia",
            event = "Aniversário Cassandra",
            date = "Terça, 14 de Março",
            peoples = 105
        ),
        SchedulesPresentation(
            salon = "Salão Magnólia",
            event = "Aniversário Morgana",
            date = "Quarta, 15 de Março",
            peoples = 90
        ),
    )
}