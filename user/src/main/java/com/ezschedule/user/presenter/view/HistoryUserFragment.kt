package com.ezschedule.user.presenter.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.user.presenter.adapter.HistoryUserAdapter
import com.ezschedule.user.presenter.viewModel.HistoryUserViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sptech.user.R
import com.sptech.user.databinding.FragmentHistoryUserBinding
import com.sptech.user.databinding.ViewUserHistoryBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryUserFragment : Fragment() {
    private lateinit var binding: FragmentHistoryUserBinding
    private lateinit var bottomSheetBinding: ViewUserHistoryBottomSheetBinding
    private val viewModel by viewModel<HistoryUserViewModel>()
    private val dialog: BottomSheetDialog by lazy {
        BottomSheetDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        isLoading(true)
        setSearchView()
    }

    private fun setObservers() = with(viewModel) {
        user.observe(viewLifecycleOwner) {
            getAllPaymentsByTenant(it.idCondominium, it.id)
            binding.swipeRefresh.setOnRefreshListener {
                getAllPixAttemps(it.cpf)
            }
        }
        historyList.observe(viewLifecycleOwner) {
            isThereContent(true)
            binding.fragRvHistory.adapter = setAdapter(it)
            binding.swipeRefresh.isRefreshing = false
            isLoading(false)
        }

        pixAttempts.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            updateHistoryWPixInfo()
        }

        empty.observe(viewLifecycleOwner) {
            isThereContent(false)
            Log.d("empty", "sem conteudo em histórico")
            isLoading(false)
        }
        error.observe(viewLifecycleOwner) {
            isThereContent(false)
            isLoading(false)
            Log.d("ERROR", "error na requisição de histórico")
        }

        viewModel.setUser(SharedPreferencesManager(requireContext()).getInfo())
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
                    viewModel.historyList.value?.let {
                        val list = it.filter { history ->
                            history.tenant.name?.contains(query, true) ?: false
                        }

                        fragRvHistory.adapter = setAdapter(list)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.historyList.value?.let {
                        val list = it.filter { history ->
                            history.tenant.name?.contains(newText, true) ?: false
                        }
                        fragRvHistory.adapter = setAdapter(list)
                    }
                }
                return false
            }
        })
    }

    private fun setAdapter(historyList: List<HistoryPresentation>): HistoryUserAdapter {
        return HistoryUserAdapter(historyList, requireContext()) { history ->
            setupBottomSheet()
            displayInfoBottomSheet(history)
        }
    }

    private fun setupBottomSheet() {
        bottomSheetBinding = ViewUserHistoryBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }

    private fun displayInfoBottomSheet(history: HistoryPresentation) = with(bottomSheetBinding) {
        history.paymentStatus.equals("PAGO").let {
            if (it) {
                tvStatusPayment.text = getString(R.string.frag_history_payment_made)
                ivPaymentStatus.setImageResource(R.drawable.correct)
                btnPaymentPix.isVisible = false
            } else {
                tvStatusPayment.text = getString(R.string.frag_history_payment_pending)
                ivPaymentStatus.setImageResource(R.drawable.error)
                btnPaymentPix.isVisible = true
                btnPaymentPix.setOnClickListener {
                    displayPaymentScreen(history)
                }
            }
        }
        tvTenantName.text = history.tenant.name
        tvTenantUnit.text = history.tenant.unit
        tvTenantApartment.text = history.tenant.apartmentNumber.toString()
        tvTenantPhone.text = history.tenant.phoneNumber
        tvEventCategory.text = history.schedule.typeEvent
        tvEventBlock.text = history.saloon.blockEvent
        tvEventPrice.text = "R$${history.saloon.saloonPrice}"
        tvEventData.text = history.paymentDate?.substring(0, 19)?.replace("T", " ")

    }

    private fun displayPaymentScreen(history: HistoryPresentation) = with(bottomSheetBinding) {
        includePix.root.isVisible = true
        Glide.with(requireContext())
            .load(history.imageQrcode)
            .into(includePix.ivQrCode)
        includePix.tvCode.text = history.qrCode
        includePix.tvValueSaloon.text = "R$${history.saloon.saloonPrice}"
        includePix.btnCancel.isVisible = false
        groupInfo.isVisible = false
    }
}