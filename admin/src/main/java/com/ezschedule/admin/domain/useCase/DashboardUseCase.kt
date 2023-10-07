package com.ezschedule.admin.domain.useCase

import com.ezschedule.admin.data.repository.ScheduleRepository

class DashboardUseCase(private val repository: ScheduleRepository) {
    suspend fun execute(id: Int) = repository.getChartData(id)
}