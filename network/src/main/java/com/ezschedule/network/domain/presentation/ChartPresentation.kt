package com.ezschedule.network.domain.presentation

data class ChartPresentation(
    val month: Int,
    val events: Int?,
    val people: Int?
)