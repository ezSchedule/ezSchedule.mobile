package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ScheduleRepository

class ValidateScheduleUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(date: String): ResultWrapper<Boolean> {
        return repository.haveBeenScheduled(date)
    }
}