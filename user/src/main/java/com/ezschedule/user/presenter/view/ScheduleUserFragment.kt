package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.ezschedule.network.domain.presentation.SchedulesPresentation
import com.ezschedule.user.presenter.adapter.SchedulesAdapter
import com.ezschedule.user.presenter.viewModel.ScheduleUserViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.sptech.user.R
import com.sptech.user.databinding.FragmentScheduleUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class ScheduleUserFragment : Fragment() {
    private lateinit var binding: FragmentScheduleUserBinding
    private val viewModel by viewModel<ScheduleUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSchedules(SharedPreferencesManager(requireContext()).getInfo().id)
        setupLoading(true)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        schedules.observe(viewLifecycleOwner) {
            binding.btnFinal.isVisible = true
            setupViewPager(it)
            setupClickFloatingButton(it.size.inc())
            setupLoading(false)
        }
        empty.observe(viewLifecycleOwner) {
            binding.btnFinal.isVisible = false
            Toast.makeText(requireContext(), "Empty", Toast.LENGTH_LONG).show()
            setupLoading(false)
        }
        error.observe(viewLifecycleOwner) {
            binding.btnFinal.isVisible = false
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            setupLoading(false)
        }
    }

    private fun setupViewPager(list: List<SchedulesPresentation>) {
        binding.vpSchedules.apply {
            adapter = SchedulesAdapter(list, requireContext()) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fl_container_new_date, NewDateFragment())
                    .commit()
            }
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

    private fun setupClickFloatingButton(size: Int) {
        binding.btnFinal.setOnClickListener {
            binding.vpSchedules.currentItem = size
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        includeLoading.isVisible = isLoading
        vpSchedules.isVisible = isLoading.not()
        btnFinal.isVisible = isLoading.not()
    }
}