package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterEventsBinding
import com.ezschedule.network.R.color.colorVariant
import com.ezschedule.network.R.color.red
import com.ezschedule.network.domain.presentation.EventItemPresentation

class CalendarEventsAdapter(
    private val dataList: List<EventItemPresentation>
) : RecyclerView.Adapter<CalendarEventsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarEventsAdapter.ViewHolder {
        val binding = AdapterEventsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarEventsAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(private val binding: AdapterEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            if (getData().isCanceled) tvNameTenant.apply {
                text = getData().nameEvent
                setTextColor(resources.getColor(red, context.theme))
            } else tvNameTenant.apply {
                text = getData().nameTenant
                setTextColor(resources.getColor(colorVariant, context.theme))
            }
            tvLabelSalon.isVisible = getData().isCanceled.not()
            tvContentSalon.text = getData().nameSalon
            tvContentDate.text = getData().date
        }

        private fun getData() = dataList[adapterPosition]
    }
}