package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.network.R
import com.ezschedule.admin.databinding.FragmentDashboardBinding
import com.ezschedule.admin.presenter.adapter.EventsAdapter
import com.ezschedule.admin.presenter.utils.CustomMarkerView
import com.ezschedule.admin.presenter.utils.CustomXValueFormatter
import com.ezschedule.admin.presenter.viewmodel.DashboardViewModel
import com.ezschedule.network.R.color.black
import com.ezschedule.network.R.color.gray_opacity
import com.ezschedule.network.R.color.white
import com.ezschedule.network.domain.presentation.ChartPresentation
import com.ezschedule.utils.ResourceWrapper
import com.ezschedule.utils.SharedPreferencesManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel by viewModel<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getChartData(SharedPreferencesManager(requireContext()).getInfo().idCondominium)
        setupLoading(true)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        chartData.observe(viewLifecycleOwner) {
            setupChart(it)
            setupRecyclerView(it)
            setupLoading(false)
        }
        empty.observe(viewLifecycleOwner) {
            setupChart(ChartPresentation.getDataEmptyList())
            setupRecyclerView(ChartPresentation.getDataEmptyList())
            setupLoading(false)
        }
        error.observe(viewLifecycleOwner) {
            setupLoading(false)
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView(historyList: List<ChartPresentation>) {
        binding.fragDashRvEvents.adapter =
            EventsAdapter(historyList, ResourceWrapper(requireContext()))
    }

    private fun setupChart(historyList: List<ChartPresentation>) {
        binding.fragDashChart.apply {
            data = setupLineDataSet(historyList)
            marker = CustomMarkerView(requireContext(), com.ezschedule.admin.R.layout.view_popup_chart)
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

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        fragDashClChart.isVisible = isLoading.not()
        includeLoading.isVisible = isLoading
    }
}