package com.ezschedule.network.domain.useCase.schedule

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.ScheduleRepository
import com.ezschedule.network.domain.response.ChartListResponse

class GetDashboardData(private val repository: ScheduleRepository) {
    suspend operator fun invoke(id: Int): ResultWrapper<ChartListResponse> {
        return repository.getChartData(id)
    }
}