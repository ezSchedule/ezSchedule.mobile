package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
        isLoading(true)
        setSearchView()
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
                binding.fragRvHistory.adapter = setAdapter(it)
            }
            isLoading(false)
        }
    }

    private fun isThereContent(thereIS: Boolean) = with(binding) {
        fragRvHistory.isVisible = thereIS
        fragHistoryTvNoPayments.isVisible = !thereIS
    }

    private fun isLoading(loading: Boolean) = with(binding) {
        viewLoading.isVisible = loading
    }


    private fun setSearchView() = with(binding) {
        fragHistorySvHistory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val list = viewmodel.scheduleList.value!!.filter { service ->
                        service.name.contains(query, true)
                    }
                    fragRvHistory.adapter = setAdapter(list)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val list = viewmodel.scheduleList.value!!.filter { service ->
                        service.name.contains(newText, true)
                    }
                    fragRvHistory.adapter = setAdapter(list)
                }
                return false
            }
        })
    }

    private fun setAdapter(scheduleList: List<ScheduleResponse>): HistoryAdapter {
        return HistoryAdapter(scheduleList) { schedule ->
            HistoryBottomSheetFragment(schedule).show(childFragmentManager, "LOL")
        }
    }

}