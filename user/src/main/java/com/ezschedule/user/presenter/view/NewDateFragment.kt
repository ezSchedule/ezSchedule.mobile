package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.network.domain.data.SalonData
import com.ezschedule.user.presenter.viewModel.NewDateViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.sptech.user.R
import com.sptech.user.databinding.FragmentNewDateBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewDateFragment : Fragment() {
    private lateinit var binding: FragmentNewDateBinding
    private val viewModel by viewModel<NewDateViewModel>()
    private val date by lazy {
        arguments?.getLong(DATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setupClickOnInfoScreen()
        setupClickOnPaymentScreen()
        setEventTypeAdapter()
        setupDatePicker()

        date?.let {
            formatterData(Date(it))
        }

        viewModel.user = SharedPreferencesManager(requireContext()).getInfo()
        viewModel.getSaloon()
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    fun formatterData(date: Date) =
        setDate(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date))

    private fun setObservers() = with(viewModel) {
        saloon.observe(viewLifecycleOwner) {
            setupSalonAdapter(it)
        }
        empty.observe(viewLifecycleOwner) {

        }
    }

    private fun setupSalonAdapter(it: List<SalonData>) {
        val saloonNameList = arrayListOf<String>()
        it.forEach { saloonData -> saloonNameList.add(saloonData.name) }
        val adapter = ArrayAdapter(
            requireContext(), R.layout.adapter_auto_complete_new_date, saloonNameList
        )
        binding.includeInfo.acSaloon.setAdapter(adapter)
        binding.includeInfo.acSaloon.setOnItemClickListener { _, _, i, _ ->
            binding.includeInfo.tvValueSaloon.text = String.format("R$%.2f", it[i].price)
        }
    }

    private fun setupClickOnInfoScreen() = with(binding.includeInfo) {
        btnBack.setOnClickListener {
            onDestroy()
        }
        btnNext.setOnClickListener {
            setupLayout(false)
        }
    }

    private fun setupClickOnPaymentScreen() = with(binding.includePayment) {
        btnCancel.setOnClickListener {
            setupLayout(true)
        }
    }

    private fun setupDatePicker() {
        binding.includeInfo.edtEventDate.setOnClickListener {
            DatePickerFragment().show(childFragmentManager, "datePicker")
        }
    }

    private fun setDate(date: String) {
        binding.includeInfo.edtEventDate.setText(date)
    }

    private fun setupLayout(isInfoScreen: Boolean) = binding.apply {
        includeInfo.root.isVisible = isInfoScreen
        includePayment.root.isVisible = isInfoScreen.not()
    }

    private fun setEventTypeAdapter() = with(binding) {
        val arrayString = resources.getStringArray(R.array.list_events)
        val adapter =
            ArrayAdapter(requireContext(), R.layout.adapter_auto_complete_new_date, arrayString)
        includeInfo.acEventType.setAdapter(adapter)
    }

    private companion object {
        const val DATE = "date"
    }
}