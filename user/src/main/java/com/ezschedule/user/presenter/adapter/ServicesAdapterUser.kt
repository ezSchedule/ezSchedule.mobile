package com.ezschedule.user.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.sptech.user.databinding.AdapterServiceUserBinding

@Suppress("UNCHECKED_CAST")
class ServicesAdapterUser(
    private val dataList: List<ServicePresentation>,
    private val context: Context,
    private val onClick: (ServicePresentation) -> Unit
) : RecyclerView.Adapter<ServicesAdapterUser.ViewHolder>(), Filterable {

    private var driverFiltered = dataList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServicesAdapterUser.ViewHolder {
        val binding =
            AdapterServiceUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = driverFiltered.size

    inner class ViewHolder(private val binding: AdapterServiceUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick(getData()) }
        }

        fun bind() = with(binding) {
            Glide.with(context)
                .load(getData().image)
                .into(ivService)
            tvServiceName.text = getData().name
            tvServiceUser.text = getData().userName
        }

        private fun getData() = driverFiltered[adapterPosition]
    }

    override fun getFilter(): Filter {
        return driverFilter
    }

    private val driverFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val text = constraint?.toString().orEmpty()
            val resultList = ArrayList<ServicePresentation>()

            if (text.isEmpty()) resultList.addAll(dataList)
            else dataList.forEach { item ->
                if (item.name.contains(text, true)) resultList.add(item)
            }

            return FilterResults().apply {
                values = resultList
                count = resultList.size
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            setNewData(
                if (results?.values == null) emptyList()
                else results.values as List<ServicePresentation>
            )
        }
    }

    fun setNewData(filteredList: List<ServicePresentation>) {
        driverFiltered = filteredList.orEmpty()
        notifyDataSetChanged()
    }
}