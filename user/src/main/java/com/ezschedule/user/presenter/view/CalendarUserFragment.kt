package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.user.presenter.viewModel.CalendarUserViewModel
import com.ezschedule.utils.CustomCalendarClick
import com.ezschedule.utils.CustomCalendarDecorator
import com.ezschedule.utils.SharedPreferencesManager
import com.sptech.user.R
import com.sptech.user.databinding.FragmentCalendarUserBinding
import com.squareup.timessquare.CalendarCellDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarUserFragment : Fragment() {
    private lateinit var binding: FragmentCalendarUserBinding
    private val dates by lazy { mutableMapOf<Date, Int>() }
    private val user by lazy { SharedPreferencesManager(requireContext()).getInfo() }
    private val viewModel by viewModel<CalendarUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEvents(user.idCondominium)
        setupLoading(true)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        scheduleList.observe(viewLifecycleOwner) {
            it.map { event ->
                setEventOnDate(date = event.date, getColorEvent(event.isCanceled))
            }
            setupLoading(false)
        }
        emptyList.observe(viewLifecycleOwner) {
            setupLoading(isLoading = false)
            setEventOnDate(date = "01/01/1999 12:00", getColorEvent(isCanceled = false))
        }
        error.observe(viewLifecycleOwner) {
            setupLoading(isLoading = false)
            setEventOnDate(date = "01/01/1999 12:00", getColorEvent(isCanceled = false))
            Log.i("ERROR", it.message.toString())
        }
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
                childFragmentManager.beginTransaction().replace(
                    R.id.fl_container_new_date,
                    NewDateFragment().apply { arguments = bundleOf(DATE to date) }
                ).commit()
            }
        })
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        includeLoading.isVisible = isLoading
        cvCalendar.isVisible = !isLoading
    }

    private fun convertToDate(date: String) =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(date) ?: ""
            )
        )!!

    private companion object {
        const val DATE = "DATE"
    }
}