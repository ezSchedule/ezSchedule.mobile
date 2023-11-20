package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sptech.user.R
import com.sptech.user.databinding.ViewHistoryBottomSheetBinding
import java.time.LocalDateTime
import java.time.ZoneId

class HistoryUserBottomSheetFragment(val history: HistoryPresentation) :
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
        if (!history.schedule.isCanceled!!) {
            tvTitle.text = "Pagamento Efetuado"
            ivPaymentStatus.setImageResource(R.drawable.correct)
        } else {
            tvTitle.text = "Pagamento Pendente"
            ivPaymentStatus.setImageResource(R.drawable.error)
        }
        tvTenantName.text = history.tenant.name
        tvTenantUnit.text = history.tenant.unit
        tvTenantApartment.text = history.tenant.apartmentNumber.toString()
        tvTenantPhone.text = history.tenant.phoneNumber
        tvEventCategory.text = history.schedule.typeEvent
        tvEventBlock.text = history.saloon.blockEvent
        tvEventPrice.text = "R$${history.saloon.saloonPrice}"
        val date = LocalDateTime.ofInstant(
            history.paymentDate?.toDate()?.toInstant(),
            ZoneId.systemDefault()
        ).toString()
        tvEventData.text = date.substring(0, date.indexOf(".")).replace("T", " ").replace("-", "/")
    }

}