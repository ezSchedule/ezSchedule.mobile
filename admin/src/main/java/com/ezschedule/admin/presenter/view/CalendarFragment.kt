package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentCalendarBinding
import com.ezschedule.admin.presenter.utils.CustomCalendarClick
import com.ezschedule.admin.presenter.utils.CustomCalendarDecorator
import com.ezschedule.network.R.color.light_white
import com.ezschedule.network.R.color.red
import com.ezschedule.utils.CustomBottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.timessquare.CalendarCellDecorator
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val dates = mutableMapOf<Date, Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setEventOnDate("2023-08-10", red)
        setEventOnDate("2023-08-12", red)
        setEventOnDate("2023-08-23", red)
        setEventOnDate("2024-08-22", red)

        setEventOnDate("2023-08-30", light_white)
        setEventOnDate("2023-08-20", light_white)
        setEventOnDate("2023-11-20", light_white)

        setupClickButtons()
    }

    private fun setupClickButtons() {
        with(binding) {
            fragCalendarBtnCalendar.setOnClickListener {
                setLayoutChange(isCalendarVisible = true)
            }
            fragCalendarBtnCanceled.setOnClickListener {
                setLayoutChange(isCalendarVisible = false)
                includeCalendarBottomSheet.root.isVisible = false
            }
        }
    }

    private fun setLayoutChange(isCalendarVisible: Boolean) = with(binding) {
        cvCalendar.isVisible = isCalendarVisible
        fragCalendarBtnCanceled.isEnabled = isCalendarVisible
        fragCanceledGroup.isVisible = !isCalendarVisible
        fragCalendarBtnCalendar.isEnabled = !isCalendarVisible
    }

    private fun setEventOnDate(date: String, color: Int) {
        dates[SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)!!] =
            ContextCompat.getColor(requireContext(), color)
        reloadDatesList()
    }

    private fun reloadDatesList() {
        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)
        val decorators = mutableListOf<CalendarCellDecorator>()
        decorators.add(CustomCalendarDecorator(requireContext(), dates))

        with(binding) {
            cpvCalendar.decorators = decorators
            cpvCalendar.init(Date(), nextYear.time)
                .withSelectedDate(Date())
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
}