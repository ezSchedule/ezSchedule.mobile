package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterHistoryBinding
import com.ezschedule.network.domain.response.ScheduleResponse

class HistoryAdapter(
    private val histories: List<ScheduleResponse>, private val isRequest: Boolean
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

            if(!isRequest) {
                if (!historyData().isCanceled) {
                    fragHistoryImgAccept.visibility = View.GONE
                    fragHistoryImgError.visibility = View.VISIBLE
                } else {
                    fragHistoryImgAccept.visibility = View.VISIBLE
                    fragHistoryImgError.visibility = View.GONE
                }
            }else{
                fragHistoryImgAccept.isVisible = true
                fragHistoryImgError.isVisible = true

                fragHistoryImgError.setOnClickListener{
                    Toast.makeText(it.context,"works",Toast.LENGTH_SHORT).show()
                }
                fragHistoryImgAccept.setOnClickListener {
                    Toast.makeText(it.context,"works",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun historyData() = histories[adapterPosition]
    }
}