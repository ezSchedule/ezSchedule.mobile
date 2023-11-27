package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.user.data.repository.ScheduleUserRepository

class ValidateScheduleUseCase(private val repository: ScheduleUserRepository) {
    suspend operator fun invoke(date: String): ResultWrapper<Boolean> {
        return repository.haveBeenScheduled(date)
    }
}