package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.calendar.ScheduleData
import retrofit2.http.GET
import retrofit2.http.Path

interface CalendarEndpoint {
    @GET("schedules/condominium/{id}")
    suspend fun getSchedules(@Path("id") id: Int): List<ScheduleData>
}