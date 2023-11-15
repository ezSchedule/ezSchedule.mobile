package com.ezschedule.network.domain.response

data class ChartListResponse(
    val chartList: List<ChartResponse>
) {
    fun toChartsPresentation() = chartList.map {
        it.toChartPresentation()
    }
}