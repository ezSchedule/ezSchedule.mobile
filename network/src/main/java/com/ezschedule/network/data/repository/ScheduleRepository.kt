package com.ezschedule.network.data.repository

import com.ezschedule.network.data.api.ScheduleEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ScheduleData
import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.domain.response.ChartListResponse
import com.ezschedule.network.domain.response.ReportResponse
import com.ezschedule.network.domain.response.SchedulesResponse

class ScheduleRepository(private val endpoint: ScheduleEndpoint) {
    suspend fun getSchedulesByTenant(id: Int): ResultWrapper<SchedulesResponse> {
        return requestHandler {
            endpoint.getSchedulesTenant(id).toResponse()
        }
    }

    suspend fun getSchedulesCancellations(id: Int): ResultWrapper<SchedulesResponse> {
        return requestHandler {
            endpoint.getSchedules(id).toResponse()
        }
    }

    suspend fun createSchedule(scheduleRequest: ScheduleRequest): ResultWrapper<ReportResponse> {
        return requestHandler {
            endpoint.createSchedule(scheduleRequest)
        }
    }

    suspend fun haveBeenScheduled(date: String): ResultWrapper<Boolean> {
        return requestHandler {
            endpoint.haveBeenSchedules(date)
        }
    }

    suspend fun getSchedules(id: Int): ResultWrapper<SchedulesResponse> {
        return requestHandler {
            endpoint.getSchedules(id).toResponse()
        }
    }

    suspend fun sendCanceledDay(schedule: ScheduleData): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.sendCanceledDay(schedule)
        }
    }

    suspend fun getChartData(id: Int): ResultWrapper<ChartListResponse> {
        return requestHandler {
            endpoint.getChartData(id).toResponse()
        }
    }
}