package com.ezschedule.utils

import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener
import java.util.Date

class CustomCalendarClick(private val onClick: (date: Date?) -> Unit) : OnDateSelectedListener {
    override fun onDateSelected(date: Date?) = onClick(date)

    override fun onDateUnselected(date: Date?) {}
}