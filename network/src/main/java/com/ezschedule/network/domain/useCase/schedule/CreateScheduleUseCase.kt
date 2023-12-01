package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ScheduleRepository
import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.domain.response.ReportResponse

class CreateScheduleUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(scheduleRequest: ScheduleRequest): ResultWrapper<ReportResponse> {
        return repository.createSchedule(scheduleRequest)
    }
}
