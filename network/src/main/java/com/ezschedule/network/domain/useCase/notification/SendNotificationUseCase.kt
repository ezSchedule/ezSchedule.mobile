package com.ezschedule.network.domain.useCase.notification

import com.ezschedule.network.data.repository.NotificationRepository
import com.ezschedule.network.domain.data.NotificationRequest

class SendNotificationUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(notification: NotificationRequest) =
        repository.sendNotification(notification)
}