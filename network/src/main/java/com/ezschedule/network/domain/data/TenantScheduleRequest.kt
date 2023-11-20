package com.ezschedule.network.domain.data

data class TenantScheduleRequest(
    val id: Int,
    val name: String,
    val residentsBlock: String,
    val apartmentNumber: Int,
    val phoneNumber: String,
    val condominium: CondominiumRequest
)