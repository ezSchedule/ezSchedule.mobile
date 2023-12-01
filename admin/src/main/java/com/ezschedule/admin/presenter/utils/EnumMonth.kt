package com.ezschedule.admin.presenter.utils

import com.ezschedule.network.R

enum class EnumMonth(
    private val number: Int,
    private val monthString: Int
) {
    JANUARY(1, R.string.frag_dashboard_month_january),
    FEBRUARY(2, R.string.frag_dashboard_month_february),
    MARCH(3, R.string.frag_dashboard_month_march),
    APRIL(4, R.string.frag_dashboard_month_april),
    MAY(5, R.string.frag_dashboard_month_may),
    JUNE(6, R.string.frag_dashboard_month_june),
    JULY(7, R.string.frag_dashboard_month_july),
    AUGUST(8, R.string.frag_dashboard_month_august),
    SEPTEMBER(9, R.string.frag_dashboard_month_september),
    OCTOBER(10, R.string.frag_dashboard_month_october),
    NOVEMBER(11, R.string.frag_dashboard_month_november),
    DECEMBER(12, R.string.frag_dashboard_month_december);

    companion object {
        fun getDate(number: Int) = values().find { it.number == number }?.monthString ?: 0
    }
}