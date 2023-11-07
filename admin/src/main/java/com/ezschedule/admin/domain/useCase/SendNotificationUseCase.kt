package com.ezschedule.admin.domain.useCase

import com.ezschedule.admin.data.repository.NotificationRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.NotificationRequest

class SendNotificationUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(notification: NotificationRequest): ResultWrapper<Unit> =
        repository.sendNotification(notification)
}