package com.ezschedule.network.domain.response

data class SchedulesResponse(
    val schedules: List<ScheduleResponse>
) {
    fun toEventsPresentation() = schedules.map {
        it.toEventPresentation()
    }

    fun toSchedulesPresentation() = schedules.map {
        it.toSchedulePresentation()
    }
}