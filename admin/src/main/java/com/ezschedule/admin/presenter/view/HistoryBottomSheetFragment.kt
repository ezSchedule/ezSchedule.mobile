package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.ViewHistoryBottomSheetBinding
import com.ezschedule.network.domain.response.ScheduleResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HistoryBottomSheetFragment(val schedule: ScheduleResponse) : BottomSheetDialogFragment() {

    private lateinit var binding: ViewHistoryBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewHistoryBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() = with(binding) {
        if (!schedule.isCanceled) {
            tvTitle.text = "Pagamento Efetuado"
            ivPaymentStatus.setImageResource(R.drawable.correct)
        } else {
            tvTitle.text = "Pagamento Pendente"
            ivPaymentStatus.setImageResource(R.drawable.error)
        }
        tvTenantName.text = schedule.nameTenant
        tvTenantUnit.text = "2B"
        tvTenantApartment.text = "42"
        tvTenantPhone.text = "955555121"
        tvEventCategory.text = schedule.type
        tvEventBlock.text = schedule.blockSalon
        tvEventData.text = schedule.date
        tvEventPrice.text = schedule.priceSalon.toString()
    }

}