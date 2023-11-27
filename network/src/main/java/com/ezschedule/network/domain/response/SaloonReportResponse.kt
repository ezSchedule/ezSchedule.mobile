package com.ezschedule.network.domain.response

data class SaloonReportResponse(
    val id: Int,
    val name: String,
    val saloonPrice: Double,
    val blockEvent: String
)