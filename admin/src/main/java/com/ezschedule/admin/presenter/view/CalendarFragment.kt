package com.ezschedule.admin.presenter.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentCalendarBinding
import com.ezschedule.admin.presenter.adapter.CalendarEventsAdapter
import com.ezschedule.admin.presenter.utils.CustomCalendarClick
import com.ezschedule.admin.presenter.utils.CustomCalendarDecorator
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.network.domain.data.ScheduleData
import com.ezschedule.network.domain.presentation.EventItemPresentation
import com.ezschedule.utils.CustomBottomSheetCallback
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.timessquare.CalendarCellDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel by viewModel<CalendarViewModel>()
    private val dates = mutableMapOf<Date, Int>()
    private var dateCancel: LocalDateTime? = null

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
        emptyList.observe(viewLifecycleOwner) {
            setupLoading(isLoading = false)
            setEventOnDate(date = "01/01/1999 12:00", getColorEvent(isCanceled = false))
            binding.fragCalendarTvListEmpty.isVisible = true
        }
        successfulCancellation.observe(viewLifecycleOwner) {
            getEvents(SharedPreferencesManager(requireContext()).getInfo().idCondominium)
        }
        error.observe(viewLifecycleOwner) {
            Log.i("ERROR", it.message.toString())
            setupLoading(false)
        }
    }

    private fun setupRecyclerView(dataList: List<EventItemPresentation>) {
        val adapter = CalendarEventsAdapter(dataList)
        binding.fragCalendarRvEvents.adapter = adapter
        setupSearchField(adapter)
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
            includeCalendarBottomSheet.btnConfirm.setOnClickListener {
                setupLoading(true)
                includeCalendarBottomSheet.root.isVisible = false
                view?.let { view -> requireContext().hideKeyboard(view) }
                viewModel.sendCancelDay(
                    ScheduleData.cancelDay(
                        reason = includeCalendarBottomSheet.tietReason.text.toString(),
                        date = dateCancel.toString(),
                        idTenant = SharedPreferencesManager(requireContext()).getInfo().id
                    )
                )
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
                dateCancel = convertToDateTime(date)
            }
        })
    }

    private fun setupSearchField(adapter: CalendarEventsAdapter) {
        binding.fragCalendarSvEvents.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
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
        includeLoading.apply {
            isVisible = isLoading
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun convertToDate(date: String) =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(date) ?: ""
            )
        )!!

    private fun convertToDateTime(date: Date) =
        LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}