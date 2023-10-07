package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.ScheduleData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleEndpoint {
    @GET("schedules/condominium/{id}")
    suspend fun getSchedules(@Path("id") id: Int): List<ScheduleData>

    @POST("schedules/")
    suspend fun sendCanceledDay(@Body schedule: ScheduleData)
}