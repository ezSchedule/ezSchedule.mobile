package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.repository.ScheduleRepository

class DashboardUseCase(private val repository: ScheduleRepository) {
    suspend fun execute(id: Int) = repository.getChartData(id)
}