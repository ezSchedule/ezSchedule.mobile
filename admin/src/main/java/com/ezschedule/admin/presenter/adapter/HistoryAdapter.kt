package com.ezschedule.admin.presenter.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterHistoryBinding
import com.ezschedule.network.domain.presentation.HistoryPresentation

class HistoryAdapter(
    private val histories: List<HistoryPresentation>) :
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
            tvHistoryName.text = historyData().username
            tvHistorySaloonName.text = historyData().saloonName
            tvHistorySaloonValue.text = historyData().saloonValue.toString()

            if(historyData().paymentStatus == false){
                fragHistoryImgAccept.visibility = View.GONE
                fragHistoryImgError.visibility = View.VISIBLE
            }else{
                fragHistoryImgAccept.visibility = View.VISIBLE
                fragHistoryImgError.visibility = View.GONE
            }
        }

        private fun historyData() = histories[adapterPosition]
    }
}