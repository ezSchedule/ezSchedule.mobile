package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.ViewHistoryBottomSheetBinding
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HistoryBottomSheetFragment(private val history: HistoryPresentation) :
    BottomSheetDialogFragment() {

    private lateinit var binding: ViewHistoryBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewHistoryBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() = with(binding) {
        if (history.paymentStatus.equals("PAGO")) {
            tvTitle.text = getString(R.string.frag_history_payment_made)
            ivPaymentStatus.setImageResource(R.drawable.correct)
        } else {
            tvTitle.text = getString(R.string.frag_history_payment_pending)
            ivPaymentStatus.setImageResource(R.drawable.error)
        }
        tvTenantName.text = history.tenant.name
        tvTenantUnit.text = history.tenant.unit
        tvTenantApartment.text = history.tenant.apartmentNumber.toString()
        tvTenantPhone.text = history.tenant.phoneNumber
        tvEventCategory.text = history.schedule.typeEvent
        tvEventBlock.text = history.saloon.blockEvent
        tvEventPrice.text = context?.getString(R.string.frag_history_tv_value_currency, history.saloon.saloonPrice)
        tvEventData.text =  history.paymentDate?.substring(0, 19)?.replace("T", " ")
    }

}