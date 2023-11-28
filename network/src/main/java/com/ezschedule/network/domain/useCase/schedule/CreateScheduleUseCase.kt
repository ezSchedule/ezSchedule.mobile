package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.data.repository.ScheduleRepository

class CreateScheduleUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(scheduleRequest: ScheduleRequest) =
        repository.createSchedule(scheduleRequest)
}
