package com.ezschedule.network.domain.response

data class TenantReportResponse(
    val id: Int,
    val name: String,
    val unit: String,
    val apartmentNumber: Int,
    val phoneNumber: String,
)