package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.ChartData
import com.ezschedule.network.domain.response.ChartListResponse
import com.ezschedule.network.domain.response.ChartResponse

fun List<ChartData>.toResponse() = ChartListResponse(
    chartList = this.map {
        it.toResponse()
    }
)

fun ChartData.toResponse() = ChartResponse(
    month = this.month,
    events = this.totalEvents,
    people = this.totalGuests
)