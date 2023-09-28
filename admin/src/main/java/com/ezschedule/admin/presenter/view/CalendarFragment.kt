package com.ezschedule.admin.presenter.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentCalendarBinding
import com.ezschedule.admin.presenter.adapter.CalendarEventsAdapter
import com.ezschedule.admin.presenter.utils.CustomCalendarClick
import com.ezschedule.admin.presenter.utils.CustomCalendarDecorator
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.network.domain.presentation.EventItemPresentation
import com.ezschedule.utils.CustomBottomSheetCallback
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.timessquare.CalendarCellDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel by viewModel<CalendarViewModel>()
    private val dates = mutableMapOf<Date, Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEvents(SharedPreferencesManager(requireContext()).getInfo().idCondominium)
        setupLoading(true)
        setupObservers()
        setupClickButtons()
    }

    private fun setupObservers() = with(viewModel) {
        scheduleList.observe(viewLifecycleOwner) {
            it.map { event ->
                setEventOnDate(date = event.date, getColorEvent(event.isCanceled))
            }
            setupRecyclerView(it)
            setupLoading(false)
        }
    }

    private fun setupRecyclerView(dataList: List<EventItemPresentation>) {
        binding.fragCanceledRvCanceled.adapter = CalendarEventsAdapter(dataList)
    }

    private fun setupClickButtons() {
        with(binding) {
            fragCalendarBtnCalendar.setOnClickListener {
                setLayoutChange(isCalendarVisible = true)
            }
            fragCalendarBtnEvents.setOnClickListener {
                setLayoutChange(isCalendarVisible = false)
                includeCalendarBottomSheet.root.isVisible = false
            }
        }
    }

    private fun setLayoutChange(isCalendarVisible: Boolean) = with(binding) {
        cvCalendar.isVisible = isCalendarVisible
        fragCalendarBtnEvents.isEnabled = isCalendarVisible
        fragCanceledGroup.isVisible = !isCalendarVisible
        fragCalendarBtnCalendar.isEnabled = !isCalendarVisible
    }

    private fun setEventOnDate(date: String, color: Int) {
        dates[convertToDate(date)] = ContextCompat.getColor(requireContext(), color)
        reloadDatesList()
    }

    private fun reloadDatesList() {
        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)
        val decorators = mutableListOf<CalendarCellDecorator>()
        decorators.add(CustomCalendarDecorator(requireContext(), dates))

        with(binding) {
            cpvCalendar.decorators = decorators
            cpvCalendar.init(Date(), nextYear.time).withSelectedDate(Date())
        }

        setupClickOnDay()
    }

    private fun setupClickOnDay() {
        binding.cpvCalendar.setOnDateSelectedListener(CustomCalendarClick {
            it?.let { date ->
                setupBottomSheet(DateFormat.getDateInstance(DateFormat.FULL).format(date))
            }
        })
    }

    private fun setupBottomSheet(date: String) {
        with(binding.includeCalendarBottomSheet) {
            BottomSheetBehavior.from(this.root).apply {
                addBottomSheetCallback(CustomBottomSheetCallback())
                state = BottomSheetBehavior.STATE_EXPANDED
                peekHeight = 100
            }
            root.isVisible = true
            tvDate.text = date
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        cvCalendar.isVisible = !isLoading
        fragCalendarBtnCalendar.isVisible = !isLoading
        fragCalendarBtnEvents.isVisible = !isLoading
        includeLoading.root.apply {
            isVisible = isLoading
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun convertToDate(date: String): Date {
        val toString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(date) ?: ""
        )
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(toString)!!
    }
}