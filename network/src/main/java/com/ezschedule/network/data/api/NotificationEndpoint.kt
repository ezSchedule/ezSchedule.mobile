package com.ezschedule.network.data.api

import com.ezschedule.network.domain.data.NotificationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationEndpoint {
    @POST("notification")
    suspend fun sendNotification(@Body notification: NotificationRequest)

}