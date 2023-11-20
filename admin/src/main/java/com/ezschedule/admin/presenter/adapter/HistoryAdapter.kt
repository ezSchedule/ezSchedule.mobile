package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterHistoryBinding
import com.ezschedule.network.domain.presentation.HistoryPresentation

class HistoryAdapter(
    private val histories: List<HistoryPresentation>,
    private val bottomSheet: ((HistoryPresentation) -> Unit)
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
            tvHistoryName.text = historyData().tenant.name
            tvHistorySaloonName.text = historyData().saloon.name
            tvHistorySaloonValue.text = "R$${historyData().saloon.saloonPrice}"
            fragHistoryImgAccept.isVisible = !historyData().schedule.isCanceled!!
            fragHistoryImgError.isVisible = historyData().schedule.isCanceled == true

            itemView.setOnClickListener {
                bottomSheet(historyData())
            }
        }

        private fun historyData() = histories[adapterPosition]
    }
}