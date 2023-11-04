package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.ChartPresentation

data class ChartResponse(
    val month: Int,
    val events: Int,
    val people: Int
) {
    fun toChartPresentation() = ChartPresentation(
        month = month,
        events = events,
        people = people
    )
}