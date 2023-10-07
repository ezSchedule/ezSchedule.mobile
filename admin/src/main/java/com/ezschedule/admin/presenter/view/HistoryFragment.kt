package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentHistoryBinding
import com.ezschedule.admin.presenter.adapter.HistoryAdapter
import com.ezschedule.admin.presenter.viewmodel.HistoryViewModel
import com.ezschedule.network.domain.response.ScheduleResponse
import com.ezschedule.utils.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewmodel by viewModel<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewmodel.setUser(SharedPreferencesManager(requireContext()).getInfo())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnclick()
    }

    private fun setObservers() {
        viewmodel.user.observe(this) {
            viewmodel.getUserPayments()
        }

        viewmodel.scheduleList.observe(this) {
            if (it.isEmpty()) {
                isThereContent(false)
            } else {
                isThereContent(true)
                binding.fragRvHistory.adapter = HistoryAdapter(it, false)
            }
        }
    }

    private fun setOnclick() = with(binding) {
        fragBtnRequestsHistory.setOnClickListener {
            it.isEnabled = false
            fragBtnPaymentsHistory.isEnabled = true
            val testlist = emptyList<ScheduleResponse>()
            if (testlist.isEmpty()) {
                isThereContent(false)
            } else {
                fragRvHistory.adapter = HistoryAdapter(testlist, true)
                isThereContent(true)
            }
            fragRvHistory.adapter = HistoryAdapter(emptyList(), true)
        }
        fragBtnPaymentsHistory.setOnClickListener {
            it.isEnabled = false
            fragBtnRequestsHistory.isEnabled = true
            if (viewmodel.scheduleList.value!!.isEmpty()) {
                isThereContent(false)
            } else {
                fragRvHistory.adapter =
                    HistoryAdapter(viewmodel.scheduleList.value!!, false)
                isThereContent(true)
            }
        }
    }

    private fun isThereContent(thereIS: Boolean) = with(binding) {
        fragRvHistory.isVisible = thereIS
        fragHistoryTvNoPayments.isVisible = !thereIS
    }

}