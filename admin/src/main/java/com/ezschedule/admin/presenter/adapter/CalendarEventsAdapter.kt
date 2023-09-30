package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterEventsBinding
import com.ezschedule.network.R.color.colorVariant
import com.ezschedule.network.R.color.red
import com.ezschedule.network.domain.presentation.EventItemPresentation

@Suppress("UNCHECKED_CAST")
class CalendarEventsAdapter(
    private val dataList: List<EventItemPresentation>
) : RecyclerView.Adapter<CalendarEventsAdapter.ViewHolder>(), Filterable {

    private var driverFiltered = dataList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarEventsAdapter.ViewHolder {
        val binding = AdapterEventsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarEventsAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = driverFiltered.size

    inner class ViewHolder(private val binding: AdapterEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            if (getData().isCanceled) tvNameTenant.apply {
                text = getData().nameEvent
                setTextColor(resources.getColor(red, context.theme))
            } else tvNameTenant.apply {
                text = getData().nameTenant
                setTextColor(resources.getColor(colorVariant, context.theme))
            }
            tvLabelSalon.isVisible = getData().isCanceled.not()
            tvContentSalon.text = getData().nameSalon
            tvContentDate.text = getData().date
        }

        private fun getData() = driverFiltered[adapterPosition]
    }

    override fun getFilter(): Filter {
        return driverFilter
    }

    private val driverFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val text = constraint?.toString().orEmpty()
            val resultList = ArrayList<EventItemPresentation>()

            if (text.isEmpty()) resultList.addAll(dataList)
            else dataList.forEach { item ->
                if (
                    item.nameEvent.contains(text, true) ||
                    item.nameTenant.contains(text, true)
                ) resultList.add(item)
            }

            return FilterResults().apply {
                values = resultList
                count = resultList.size
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            setNewData(
                if (results?.values == null) emptyList()
                else results.values as List<EventItemPresentation>
            )
        }
    }

    fun setNewData(data: List<EventItemPresentation>?) {
        driverFiltered = data.orEmpty()
        notifyDataSetChanged()
    }
}