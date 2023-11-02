package com.ezschedule.user.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.network.domain.presentation.SchedulesPresentation
import com.sptech.user.R
import com.sptech.user.databinding.AdapterScheduleBinding

class SchedulesAdapter(
    private val list: List<SchedulesPresentation>,
    private val context: Context
) : RecyclerView.Adapter<SchedulesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: AdapterScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            tvNameSalon.text = getData().salon
            tvNameEvent.text = getData().event
            tvDateEvent.text = getData().date
            tvPeopleEvent.text =
                context.getString(R.string.frag_schedule_tv_people_value, getData().peoples)
        }

        private fun getData() = list[adapterPosition]
    }
}