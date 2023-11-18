package com.ezschedule.network.domain.response

data class ScheduleReportResponse(
    val id: Int,
    val nameEvent: String,
    val typeEvent: String,
    val dateEvent: String,
    val isCanceled: Int,
    val totalNumberGuests: Int
)

