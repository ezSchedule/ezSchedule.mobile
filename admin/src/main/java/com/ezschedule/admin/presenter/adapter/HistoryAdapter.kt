package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterHistoryBinding
import com.ezschedule.admin.presenter.view.HistoryBottomSheetFragment
import com.ezschedule.network.domain.response.ScheduleResponse

class HistoryAdapter(
    private val histories: List<ScheduleResponse>,
    private val bottomSheet: ((ScheduleResponse) -> Unit)
) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = histories.size

    inner class ViewHolder(private val binding: AdapterHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            tvHistoryName.text = historyData().name
            tvHistorySaloonName.text = historyData().nameSalon
            tvHistorySaloonValue.text = historyData().priceSalon.toString()
            fragHistoryImgAccept.isVisible = !historyData().isCanceled
            fragHistoryImgError.isVisible = historyData().isCanceled

            itemView.setOnClickListener{
              bottomSheet(historyData())
            }

        }

        private fun historyData() = histories[adapterPosition]
    }
}