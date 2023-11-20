package com.ezschedule.network.domain.presentation

data class SchedulesPresentation(
    val salon: String,
    val event: String,
    val date: String,
    val peoples: Int
)