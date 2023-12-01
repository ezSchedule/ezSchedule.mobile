package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.repository.ScheduleRepository

class GetSchedulesCancellationsUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(id: Int) = repository.getSchedulesCancellations(id)
}