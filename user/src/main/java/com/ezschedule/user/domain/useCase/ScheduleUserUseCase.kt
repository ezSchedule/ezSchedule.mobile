package com.ezschedule.user.domain.useCase

import com.ezschedule.user.data.repository.ScheduleUserRepository

class ScheduleUserUseCase(private val repository: ScheduleUserRepository) {
    suspend fun execute(id: Int) = repository.getSchedules(id)
}