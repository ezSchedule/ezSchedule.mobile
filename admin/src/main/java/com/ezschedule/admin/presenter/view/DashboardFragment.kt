package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.FragmentDashboardBinding
import com.ezschedule.admin.presenter.adapter.EventsAdapter
import com.ezschedule.admin.presenter.utils.CustomMarkerView
import com.ezschedule.admin.presenter.utils.CustomXValueFormatter
import com.ezschedule.network.R.color.black
import com.ezschedule.network.R.color.gray_opacity
import com.ezschedule.network.R.color.white
import com.ezschedule.network.domain.presentation.ChartPresentation
import com.ezschedule.utils.ResourceWrapper
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart(getData())
        setupRecyclerView(getData())
    }

    private fun getData() = listOf(
        ChartPresentation(
            month = 1,
            events = 3,
            people = 54
        ),
        ChartPresentation(
            month = 2,
            events = 6,
            people = 45
        ),
        ChartPresentation(
            month = 3,
            events = 2,
            people = 54
        ),
        ChartPresentation(
            month = 4,
            events = 12,
            people = 32
        ),
        ChartPresentation(
            month = 5,
            events = 3,
            people = 32
        ),
        ChartPresentation(
            month = 6,
            events = 2,
            people = 12
        ),
        ChartPresentation(
            month = 7,
            events = 2,
            people = 34
        ),
        ChartPresentation(
            month = 8,
            events = 3,
            people = 21
        ),
        ChartPresentation(
            month = 9,
            events = 2,
            people = 45
        ),
        ChartPresentation(
            month = 10,
            events = null,
            people = null
        ),
        ChartPresentation(
            month = 11,
            events = null,
            people = null
        ),
        ChartPresentation(
            month = 12,
            events = null,
            people = null
        ),
    )

    private fun setupRecyclerView(historyList: List<ChartPresentation>) {
        binding.fragDashRvEvents.adapter =
            EventsAdapter(historyList, ResourceWrapper(requireContext()))
    }

    private fun setupChart(historyList: List<ChartPresentation>) {
        binding.fragDashChart.apply {
            data = setupLineDataSet(historyList)
            marker = CustomMarkerView(requireContext(), R.layout.view_popup_chart)
            description.isEnabled = false
            setScaleEnabled(false)
            setPinchZoom(false)
            setExtraOffsets(0f, 0f, 0f, 16f)

            axisRight.isEnabled = false

            axisLeft.isEnabled = false

            xAxis.apply {
                granularity = 1f
                textSize = 16f
                textColor =
                    ContextCompat.getColor(requireContext(), white)
                position = XAxis.XAxisPosition.BOTTOM
                axisMinimum = 0f
                axisMaximum = (historyList.size + 1).toFloat()
                setLabelCount(historyList.size + 2, true)
                valueFormatter = CustomXValueFormatter(historyList.map { it.month })
                setDrawGridLines(false)
            }

            legend.apply {
                form = Legend.LegendForm.LINE
                formSize = 24f
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                textSize = 16f
                textColor = ContextCompat.getColor(requireContext(), black)
            }
        }
    }

    private fun setupLineDataSet(historyList: List<ChartPresentation>): LineData {
        val eventsList = historyList.mapIndexed { index, list ->
            list.events?.let {
                Entry((index.inc()).toFloat(), it.toFloat())
            }
        }.filterNotNull()

        val peopleList = historyList.mapIndexed { index, list ->
            list.people?.let {
                Entry((index.inc()).toFloat(), it.toFloat())
            }
        }.filterNotNull()

        val lineDataSet1 =
            getLineDataSet(eventsList, getString(R.string.frag_dashboard_label_events), black)

        val lineDataSet2 =
            getLineDataSet(peopleList, getString(R.string.frag_dashboard_label_people), white)

        return LineData(listOf(lineDataSet1, lineDataSet2))
    }

    private fun getLineDataSet(dataList: List<Entry>, title: String, lineColor: Int) =
        LineDataSet(dataList, title).apply {
            lineWidth = 2f
            setDrawCircles(false)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = ContextCompat.getColor(requireContext(), lineColor)

            highlightLineWidth = 32f
            highLightColor = ContextCompat.getColor(requireContext(), gray_opacity)
            setDrawHorizontalHighlightIndicator(false)
            setDrawValues(false)
        }
}