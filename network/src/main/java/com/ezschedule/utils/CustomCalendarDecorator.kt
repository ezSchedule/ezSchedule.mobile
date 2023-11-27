package com.ezschedule.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.ezschedule.network.R.color.black
import com.ezschedule.network.R.color.gray
import com.ezschedule.network.R.color.white
import com.squareup.timessquare.CalendarCellDecorator
import com.squareup.timessquare.CalendarCellView
import java.util.Date

class CustomCalendarDecorator(
    private val context: Context,
    private val dateColors: Map<Date, Int>
) : CalendarCellDecorator {
    override fun decorate(cellView: CalendarCellView, date: Date) {
        when {
            cellView.isToday -> {
                cellView.apply {
                    background = setupBackgroundView(Color.BLACK)
                    dayOfMonthTextView.setTextColor(getTextColorToday())
                }
            }

            dateColors.containsKey(date) -> {
                cellView.apply {
                    background = setupBackgroundView(dateColors[date] ?: Color.RED)
                    isClickable = false
                    dayOfMonthTextView.setTextColor(getTextColorSelected())
                }
            }

            else -> {
                cellView.apply {
                    setBackgroundColor(Color.TRANSPARENT)
                    dayOfMonthTextView.setTextColor(getTextColorNormal())
                }
            }
        }
    }

    private fun setupBackgroundView(color: Int) = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 16f
        setColor(color)
    }

    private fun getTextColorSelected() = ContextCompat.getColor(context, black)

    private fun getTextColorNormal() = ContextCompat.getColor(context, gray)

    private fun getTextColorToday() = ContextCompat.getColor(context, white)
}