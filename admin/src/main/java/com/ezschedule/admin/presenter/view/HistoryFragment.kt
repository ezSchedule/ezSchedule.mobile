package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentHistoryBinding
import com.ezschedule.admin.presenter.adapter.HistoryAdapter
import com.ezschedule.admin.presenter.viewmodel.HistoryViewModel
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.utils.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewmodel by viewModel<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewmodel.setUser(SharedPreferencesManager(requireContext()).getInfo())
        isLoading(true)
        setSearchView()
    }


    private fun setObservers() = with(viewmodel) {
        user.observe(viewLifecycleOwner) {
            getAllPaymentsByCondominium(it.idCondominium)
            binding.swipeRefresh.setOnRefreshListener {
                getAllPixAttemps()
            }
        }

        historyList.observe(viewLifecycleOwner) {
            isThereContent(true)
            binding.fragRvHistory.adapter = setAdapter(it)
            isLoading(false)
        }

        pixAttempts.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            updateHistoryWPixInfo()
        }

        empty.observe(viewLifecycleOwner) {
            isThereContent(false)
            isLoading(false)
        }
        error.observe(viewLifecycleOwner) {
            isThereContent(false)
            isLoading(false)
            Log.d("ERROR", "error na requisição de histórico $it")
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
                    val list = viewmodel.historyList.value!!.filter { history ->
                        history.tenant.name?.contains(query, true) ?: false
                    }
                    fragRvHistory.adapter = setAdapter(list)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val list = viewmodel.historyList.value!!.filter { history ->
                        history.tenant.name?.contains(newText, true) ?: false
                    }
                    fragRvHistory.adapter = setAdapter(list)
                }
                return false
            }
        })
    }

    private fun setAdapter(historyList: List<HistoryPresentation>): HistoryAdapter {
        return HistoryAdapter(historyList, requireContext()) { history ->
            HistoryBottomSheetFragment(history).show(childFragmentManager, "BottomSheet")
        }
    }

}