package com.ezschedule.user.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.network.domain.presentation.SchedulesPresentation
import com.ezschedule.network.R
import com.sptech.user.databinding.AdapterAddScheduleBinding
import com.sptech.user.databinding.AdapterScheduleBinding

class SchedulesAdapter(
    private val list: List<SchedulesPresentation>,
    private val context: Context,
    private val onCLick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) =
        if (itemCount == 0 || position == (itemCount - 1)) TYPE_ADD else TYPE_SCHEDULE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ADD) ViewHolderAdd(
            AdapterAddScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) else ViewHolderSchedule(
            AdapterScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).bind()
    }

    override fun getItemCount() = list.size + 1

    inner class ViewHolderAdd(binding: AdapterAddScheduleBinding) : BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onCLick()
            }
        }

        override fun bind() {}
    }

    inner class ViewHolderSchedule(
        private val binding: AdapterScheduleBinding
    ) : BaseViewHolder(binding.root) {
        override fun bind() = with(binding) {
            tvNameSalon.text = getData().salon
            tvNameEvent.text = getData().event
            tvDateEvent.text = getData().date
            tvPeopleEvent.text =
                context.getString(R.string.frag_schedule_tv_people_value, getData().peoples)
        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind()

        fun getData() = list[adapterPosition]
    }

    companion object {
        const val TYPE_ADD = 1
        const val TYPE_SCHEDULE = 2
    }
}