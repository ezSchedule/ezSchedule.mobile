package com.ezschedule.admin.presenter.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.FragmentCalendarBinding
import com.ezschedule.admin.databinding.ViewCalendarBottomSheetBinding
import com.ezschedule.admin.presenter.adapter.CalendarEventsAdapter
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.network.domain.presentation.EventItemPresentation
import com.ezschedule.utils.CustomCalendarClick
import com.ezschedule.utils.CustomCalendarDecorator
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var bindingBottomSheet: ViewCalendarBottomSheetBinding
    private lateinit var dialog: BottomSheetDialog
    private val dates by lazy { mutableMapOf<Date, Int>() }
    private val user by lazy { SharedPreferencesManager(requireContext()).getInfo() }
    private val viewModel by viewModel<CalendarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEvents(user.idCondominium)
        setupLoading(true)
        setupObservers()
        setupClickButtons()
        setupBottomSheet()
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
            binding.fragCalendarTvListEmpty.isVisible = true
            setEventOnDate(date = "01/01/1999 12:00", getColorEvent(isCanceled = false))
            setupLoading(false)
        }
        error.observe(viewLifecycleOwner) {
            Log.i("ERROR", it.message.toString())
            setEventOnDate(date = "01/01/1999 12:00", getColorEvent(isCanceled = false))
            setupLoading(false)
        }
        successfulCancellation.observe(viewLifecycleOwner) {
            getEvents(user.idCondominium)
        }
        notBlank.observe(viewLifecycleOwner) {
            setupLoading(isLoading = true)
            dialog.dismiss()
        }
        blank.observe(viewLifecycleOwner) {
            Snackbar.make(
                bindingBottomSheet.root,
                requireContext().getString(R.string.frag_calendar_tv_input_blank),
                Snackbar.LENGTH_SHORT
            ).show()
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
                displayInfoBottomSheet(date)
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

    private fun setupBottomSheet() {
        bindingBottomSheet = ViewCalendarBottomSheetBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(bindingBottomSheet.root)
    }

    private fun displayInfoBottomSheet(date: Date) = with(bindingBottomSheet) {
        tvDate.text = DateFormat.getDateInstance(DateFormat.FULL).format(date)
        btnConfirm.setOnClickListener {
            setupClickBottomSheet(convertToDateTime(date))
        }
        dialog.show()
    }

    private fun setupClickBottomSheet(date: LocalDateTime) {
        viewModel.verifyField(
            bindingBottomSheet.tietReason.text.toString(),
            date.toString(),
            user.id
        )
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
}