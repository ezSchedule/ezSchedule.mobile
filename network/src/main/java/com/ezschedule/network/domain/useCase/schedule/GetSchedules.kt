package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ScheduleRepository
import com.ezschedule.network.domain.response.SchedulesResponse

class GetSchedules(private val repository: ScheduleRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<SchedulesResponse> {
        return repository.getSchedules(id)
    }
}