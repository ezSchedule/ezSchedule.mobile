package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterCanceledBinding
import com.ezschedule.network.domain.presentation.CanceledItemPresentation

class CanceledDayAdapter(
    private val dataList: List<CanceledItemPresentation>
) : RecyclerView.Adapter<CanceledDayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CanceledDayAdapter.ViewHolder {
        val binding = AdapterCanceledBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CanceledDayAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(private val binding: AdapterCanceledBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            tvNameTenant.text = getData().tenantName
            tvContentSalon.text = getData().salonName
            tvContentValue.text = getData().salonValue.toString()
        }

        private fun getData() = dataList[adapterPosition]
    }
}