package com.ezschedule.admin.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ezschedule.admin.databinding.AdapterServiceBinding
import com.ezschedule.network.domain.presentation.ServicePresentation

class ServicesAdapter(
    private val services: List<ServicePresentation>,
    private val context: Context
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = services.size

    inner class ViewHolder(private val binding: AdapterServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            tvServiceName.text = serviceData().name
            tvServiceUser.text = serviceData().userName
            Glide.with(context)
                .load(serviceData().image)
                .into(ivService)
        }

        private fun serviceData() = services[adapterPosition]
    }
}
