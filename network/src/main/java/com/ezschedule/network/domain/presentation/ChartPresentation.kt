package com.ezschedule.network.domain.presentation

data class ChartPresentation(
    val month: Int,
    val events: Int?,
    val people: Int?
) {
    companion object {
        fun getDataEmptyList() = listOf(
            ChartPresentation(
                month = 1,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 2,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 3,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 4,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 5,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 6,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 7,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 8,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 9,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 10,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 11,
                events = 0,
                people = 0
            ),
            ChartPresentation(
                month = 12,
                events = 0,
                people = 0
            ),
        )
    }
}