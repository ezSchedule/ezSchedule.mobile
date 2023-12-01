package com.ezschedule.user.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.network.R
import com.sptech.user.databinding.AdapterUserHistoryBinding

class HistoryUserAdapter(
    private val histories: List<HistoryPresentation>,
    private val context: Context,
    private val bottomSheet: ((HistoryPresentation) -> Unit)
) : RecyclerView.Adapter<HistoryUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterUserHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = histories.size

    inner class ViewHolder(private val binding: AdapterUserHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                bottomSheet(historyData())
            }
        }

        fun bind() = with(binding) {
            tvHistoryName.text = historyData().schedule.nameEvent
            tvHistorySaloonName.text = historyData().saloon.name
            tvHistorySaloonValue.text = context.getString(R.string.frag_history_tv_value_currency,historyData().saloon.saloonPrice)
            fragHistoryImgAccept.isVisible = historyData().paymentStatus.equals("PAGO")
            fragHistoryImgError.isVisible = !historyData().paymentStatus.equals("PAGO")
        }

        private fun historyData() = histories[adapterPosition]
    }
}