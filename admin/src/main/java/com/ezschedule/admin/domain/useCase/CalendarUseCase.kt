package com.ezschedule.admin.domain.useCase

import com.ezschedule.admin.data.repository.ScheduleRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ScheduleData
import com.ezschedule.network.domain.response.SchedulesResponse

class CalendarUseCase(private val repository: ScheduleRepository) {
    suspend fun execute(id: Int): ResultWrapper<SchedulesResponse> = repository.getSchedules(id)

    suspend fun cancelDate(schedule: ScheduleData): ResultWrapper<Unit> =
        repository.sendCanceledDay(schedule)
}