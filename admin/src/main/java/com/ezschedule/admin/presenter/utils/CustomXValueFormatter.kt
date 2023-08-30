package com.ezschedule.admin.presenter.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class CustomXValueFormatter(private val labels: List<Int>) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return when {
            index == 0 || index > labels.size -> EMPTY_STRING
            else -> String.format(FORMAT_STRING, labels[index - 1])
        }
    }

    companion object {
        const val EMPTY_STRING = ""
        const val FORMAT_STRING = "%d"
    }
}