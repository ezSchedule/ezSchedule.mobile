package com.ezschedule.user.data.repository

import com.ezschedule.network.data.api.ScheduleEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.domain.response.ReportResponse
import com.ezschedule.network.domain.response.SchedulesResponse

class ScheduleUserRepository(private val endpoint: ScheduleEndpoint) {
    suspend fun getSchedules(id: Int): ResultWrapper<SchedulesResponse> {
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

}