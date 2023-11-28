package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.network.R
import com.ezschedule.admin.databinding.AdapterDashboardEventsBinding
import com.ezschedule.admin.presenter.utils.EnumMonth
import com.ezschedule.network.domain.presentation.ChartPresentation
import com.ezschedule.utils.ResourceWrapper

class EventsAdapter(
    private val dataList: List<ChartPresentation>,
    private val resourceWrapper: ResourceWrapper
) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterDashboardEventsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: AdapterDashboardEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            tvMonth.text = resourceWrapper.getString(EnumMonth.getDate(getData().month))
            tvEvents.text = resourceWrapper.getString(
                R.string.frag_dashboard_text_events,
                getData().events ?: 0
            )
            tvPeople.text = resourceWrapper.getString(
                R.string.frag_dashboard_text_people,
                getData().people ?: 0
            )
        }

        private fun getData() = dataList[adapterPosition]
    }
}