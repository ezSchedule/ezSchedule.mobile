package com.ezschedule.admin.presenter.utils

import android.annotation.SuppressLint
import android.content.Context
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.ViewPopupChartBinding
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class CustomMarkerView(
    context: Context,
    layoutResource: Int
) : MarkerView(
    context,
    layoutResource
) {
    private var binding: ViewPopupChartBinding =
        ViewPopupChartBinding.bind(findViewById(R.id.cl_popup_chart))

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        var string = ""
        highlight?.let {
            string = if (it.dataSetIndex == 0)
                context.getString(com.ezschedule.network.R.string.frag_dashboard_text_events, it.y.toInt())
            else
                context.getString(com.ezschedule.network.R.string.frag_dashboard_text_people, it.y.toInt())
        }

        entry?.let {
            binding.popupTvTitle.text =
                context.getString(EnumMonth.getDate(it.x.toInt()))
            binding.popupTvValue.text = string

        }
        super.refreshContent(entry, highlight)
    }

    override fun getOffset() = MPPointF(-(width / 2f), -(height.toFloat() + 50F))
}