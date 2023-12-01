package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ScheduleRepository
import com.ezschedule.network.domain.data.ScheduleData

class CancelDateUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(schedule: ScheduleData): ResultWrapper<Unit> {
        return repository.sendCanceledDay(schedule)
    }
}