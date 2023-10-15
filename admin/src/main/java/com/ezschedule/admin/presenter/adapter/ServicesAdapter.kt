package com.ezschedule.admin.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.AdapterServiceBinding
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation

class ServicesAdapter(
    private val services: List<ServicePresentation> = emptyList(),
    private val tenants: List<TenantServicePresentation> = emptyList(),
    private val context: Context
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    val listOfSelectedItems = arrayListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        if (tenants.isNotEmpty()) {
            return tenants.size
        }
        return services.size
    }

    inner class ViewHolder(private val binding: AdapterServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            if (tenants.isNotEmpty()) {
                tvServiceName.text = tenantsData().name
                tvServiceUser.text = tenantsData().phoneNumber
                Glide.with(context)
                    .load(tenantsData().image)
                    .into(ivService)
                itemView.setOnClickListener{
                    itemView.isSelected = true
                    if(itemView.isSelected){
                        itemView.setBackgroundResource(R.drawable.item_rounded_secondary)
                    }else{
                        itemView.setBackgroundResource(R.drawable.item_rounded)
                    }
                    listOfSelectedItems.add(adapterPosition)
                }
            } else {
                tvServiceName.text = serviceData().name
                tvServiceUser.text = serviceData().userName
                Glide.with(context)
                    .load(serviceData().image)
                    .into(ivService)
            }
        }

        private fun serviceData() = services[adapterPosition]

        private fun tenantsData() = tenants[adapterPosition]
    }
}