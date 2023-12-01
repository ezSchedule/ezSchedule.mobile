package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.repository.ScheduleRepository

class GetSchedulesUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(id: Int) = repository.getSchedules(id)
}