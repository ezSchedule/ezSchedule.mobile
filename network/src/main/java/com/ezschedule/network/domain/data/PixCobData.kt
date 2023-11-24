package com.ezschedule.network.domain.data

data class PixCobData(
    val txid: String,
    val status: String,
    val pix: List<PixPaymentData>?

)
