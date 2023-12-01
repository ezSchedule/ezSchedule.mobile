package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.repository.ScheduleRepository

class GetSchedulesByTenantUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(id: Int) = repository.getSchedulesByTenant(id)
}