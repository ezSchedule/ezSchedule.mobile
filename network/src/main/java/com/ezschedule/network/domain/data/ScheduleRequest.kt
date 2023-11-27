package com.ezschedule.network.domain.data

class ScheduleRequest(
    val nameEvent: String,
    val typeEvent: String,
    val dateEvent: String,
    val isCanceled: Int,
    val totalNumberGuests: Int,
    val saloon: SalonData,
    val tenant: TenantScheduleRequest
)

