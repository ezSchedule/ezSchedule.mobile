package com.ezschedule.network.domain.data

data class NotificationRequest(
    val condominium: CondominiumRequest,
    val isEdited: Boolean?,
    val textContent: String?,
    val typeMessage: String?
)