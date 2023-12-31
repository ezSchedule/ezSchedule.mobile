package com.ezschedule.admin.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.AdapterServiceBinding
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation

class AddTenantsServicesAdapter(
    private val tenants: List<TenantServicePresentation>,
    private val context: Context,
    val itemClicked: ((Int) -> Unit?)? = null
) : RecyclerView.Adapter<AddTenantsServicesAdapter.ViewHolder>() {

    private var selectedPos = RecyclerView.NO_POSITION
    private var viewHolderList = ArrayList<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolderList.add(holder)
        holder.bind()
    }

    override fun getItemCount(): Int = tenants.size

    inner class ViewHolder(private val binding: AdapterServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
                tvServiceName.text = tenantsData().name
                tvServiceUser.text = tenantsData().phoneNumber
                Glide.with(context)
                    .load(tenantsData().image)
                    .into(ivService)
                itemView.setOnClickListener {
                    if (selectedPos >= 0) {
                        viewHolderList[selectedPos].itemView.setBackgroundResource(R.drawable.item_rounded)
                    }
                    selectedPos = adapterPosition
                    itemView.setBackgroundResource(R.drawable.item_rounded_secondary)
                    itemClicked?.let { it(selectedPos) }
                }

        }
        private fun tenantsData() = tenants[adapterPosition]
    }
}