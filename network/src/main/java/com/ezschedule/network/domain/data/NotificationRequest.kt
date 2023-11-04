package com.ezschedule.network.domain.data

data class NotificationRequest(
    val idCondominium: Long?,
    val isEdited: Boolean?,
    val textContent: String?,
    val typeMessage: String?
)