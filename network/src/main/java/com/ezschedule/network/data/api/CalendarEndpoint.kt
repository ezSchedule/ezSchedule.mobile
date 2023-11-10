package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.ChartData
import com.ezschedule.network.domain.data.ScheduleData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CalendarEndpoint {
    @GET("schedules/condominium/{id}")
    suspend fun getSchedules(@Path("id") id: Int): List<ScheduleData>

    @POST("schedules/")
    suspend fun sendCanceledDay(@Body schedule: ScheduleData)

    @GET("schedules/findSchedule/v2/{id}")
    suspend fun getChartData(@Path("id") id: Int): List<ChartData>

    @GET("schedules/tenant/{id}")
    suspend fun getSchedulesTenant(@Path("id") id: Int): List<ScheduleData>
}