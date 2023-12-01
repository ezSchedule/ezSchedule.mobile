package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.repository.ScheduleRepository

class ValidateScheduleUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(date: String) = repository.haveBeenScheduled(date)
}