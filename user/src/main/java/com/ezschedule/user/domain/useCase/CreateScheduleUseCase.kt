package com.ezschedule.user.domain.useCase

import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.user.data.repository.ScheduleUserRepository

class CreateScheduleUseCase(private val repository: ScheduleUserRepository) {
    suspend operator fun invoke(scheduleRequest: ScheduleRequest) =
        repository.createSchedule(scheduleRequest)
}
