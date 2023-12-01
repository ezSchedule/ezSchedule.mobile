package com.ezschedule.network.data.repository

import com.ezschedule.network.data.api.NotificationEndpoint
import com.ezschedule.network.data.network.handler.requestHandler
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.NotificationRequest

class NotificationRepository(private val endpoint: NotificationEndpoint) {
    suspend fun sendNotification(notification: NotificationRequest): ResultWrapper<Unit> {
        return requestHandler {
            endpoint.sendNotification(notification)
        }
    }
}