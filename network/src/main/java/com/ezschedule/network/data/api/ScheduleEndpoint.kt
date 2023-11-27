package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.ChartData
import com.ezschedule.network.domain.data.ScheduleData
import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.domain.response.ReportResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleEndpoint {
    @GET("schedules/condominium/{id}")
    suspend fun getSchedules(@Path("id") id: Int): List<ScheduleData>

    @POST("schedules/")
    suspend fun sendCanceledDay(@Body schedule: ScheduleData)

    @GET("schedules/findSchedule/v2/{id}")
    suspend fun getChartData(@Path("id") id: Int): List<ChartData>

    @GET("schedules/tenant/{id}")
    suspend fun getSchedulesTenant(@Path("id") id: Int): List<ScheduleData>

    @POST("schedules")
    suspend fun createSchedule(@Body schedule: ScheduleRequest): ReportResponse

    @GET("schedules/validate/{dateEvent}")
    suspend fun haveBeenSchedules(@Path("dateEvent") date: String): Boolean
}