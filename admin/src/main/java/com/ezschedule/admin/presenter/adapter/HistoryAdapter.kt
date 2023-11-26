package com.ezschedule.admin.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.AdapterHistoryBinding
import com.ezschedule.network.domain.presentation.HistoryPresentation

class HistoryAdapter(
    private val histories: List<HistoryPresentation>,
    private val context: Context,
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
            tvHistorySaloonValue.text = context.getString(R.string.frag_history_tv_value_currency,historyData().saloon.saloonPrice)
            fragHistoryImgAccept.isVisible = historyData().paymentStatus.equals("PAGO")
            fragHistoryImgError.isVisible = !historyData().paymentStatus.equals("PAGO")

            itemView.setOnClickListener {
                bottomSheet(historyData())
            }
        }

        private fun historyData() = histories[adapterPosition]
    }
}