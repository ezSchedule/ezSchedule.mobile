package com.ezschedule.network.domain.presentation

data class EventItemPresentation (
    val nameEvent: String,
    val nameTenant: String,
    val nameSalon: String?,
    val date: String,
    val isCanceled: Boolean
)