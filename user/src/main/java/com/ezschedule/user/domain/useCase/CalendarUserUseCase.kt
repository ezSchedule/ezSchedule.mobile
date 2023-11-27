package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.SchedulesResponse
import com.ezschedule.user.data.repository.ScheduleUserRepository

class CalendarUserUseCase(private val repository: ScheduleUserRepository) {
    suspend fun execute(id: Int): ResultWrapper<SchedulesResponse> {
        return repository.getSchedulesCancellations(id)
    }
}