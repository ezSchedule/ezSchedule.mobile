package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.user.presenter.adapter.HistoryUserAdapter
import com.ezschedule.user.presenter.viewModel.HistoryUserViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.sptech.user.databinding.FragmentHistoryUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryUserFragment : Fragment() {
    private lateinit var binding: FragmentHistoryUserBinding
    private val viewmodel by viewModel<HistoryUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewmodel.setUser(SharedPreferencesManager(requireContext()).getInfo())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoading(true)
        setSearchView()
    }

    private fun setObservers() = with(viewmodel) {
        user.observe(this@HistoryUserFragment) {
            getAllPaymentsByTenant(it.id)
        }

        historyList.observe(this@HistoryUserFragment) {
            isThereContent(true)
            binding.fragRvHistory.adapter = setAdapter(it)
            isLoading(false)
        }

        empty.observe(this@HistoryUserFragment) {
            isThereContent(false)
            isLoading(false)
        }
        error.observe(this@HistoryUserFragment) {
            isThereContent(false)
            isLoading(false)
            Log.d("ERROR", "error na requisição de histórico")
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

    private fun setAdapter(historyList: List<HistoryPresentation>): HistoryUserAdapter {
        return HistoryUserAdapter(historyList) { history ->
            HistoryUserBottomSheetFragment(history).show(childFragmentManager, "BottomSheet")
        }
    }
}