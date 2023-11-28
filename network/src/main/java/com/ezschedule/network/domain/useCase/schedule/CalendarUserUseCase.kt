package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.SchedulesResponse
import com.ezschedule.network.data.repository.ScheduleRepository

class CalendarUserUseCase(private val repository: ScheduleRepository) {
    suspend fun execute(id: Int): ResultWrapper<SchedulesResponse> {
        return repository.getSchedulesCancellations(id)
    }
}