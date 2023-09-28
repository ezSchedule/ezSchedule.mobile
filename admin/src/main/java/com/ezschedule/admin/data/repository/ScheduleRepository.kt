package com.ezschedule.admin.data.repository

import com.ezschedule.network.data.api.CalendarEndpoint
import com.ezschedule.network.data.ext.toResponse
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.SchedulesResponse

class ScheduleRepository(private val endpoint: CalendarEndpoint) {
    suspend fun getSchedules(id: Int): ResultWrapper<SchedulesResponse> {
        return requestHandler {
            endpoint.getSchedules(id).toResponse()
        }
    }
}