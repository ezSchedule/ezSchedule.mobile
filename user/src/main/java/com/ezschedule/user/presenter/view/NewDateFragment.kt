package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.data.ScheduleRequest
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
    private var saloonPos = -1

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
        viewModel.getUser(SharedPreferencesManager(requireContext()).getInfo().id)

    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    fun formatterData(date: Date) =
        setDate(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date))

    private fun setupClickOnInfoScreen() = with(binding.includeInfo) {
        btnBack.setOnClickListener {
            onDestroy()
        }

        btnNext.setOnClickListener {
            val date = edtEventDate.text.toString().split("/")
            val newDate = "${date[2]}-${date[1]}-${date[0]}T00:00:00.000"

            val scheduleRequest = ScheduleRequest(
                nameEvent = edtEventName.text.toString(),
                typeEvent = acEventType.text.toString(),
                dateEvent = newDate,
                isCanceled = 0,
                totalNumberGuests = edtQuantityGuests.text.toString().toInt(),
                saloon = viewModel.saloon.value!![saloonPos],
                tenant = viewModel.userData.value!!.toTenantScheduleRequest()
            )

            viewModel.createSchedule(scheduleRequest)
            setLoading(true)
        }
    }

    private fun setupClickOnPaymentScreen() = with(binding.includePayment) {
        btnCancel.setOnClickListener {
            setupLayout(true)
        }
    }

    private fun setObservers() = with(viewModel) {
        userData.observe(viewLifecycleOwner) {
            viewModel.getSaloon()
        }

        saloon.observe(viewLifecycleOwner) {
            val saloonNameList = arrayListOf<String>()
            it.forEach { saloonData -> saloonNameList.add(saloonData.name) }
            val adapter = ArrayAdapter(
                requireContext(), R.layout.adapter_auto_complete_new_date, saloonNameList
            )
            binding.includeInfo.acSaloon.setAdapter(adapter)
            binding.includeInfo.acSaloon.setOnItemClickListener { adapterView, view, i, l ->
                saloonPos = i
                binding.includeInfo.tvValueSaloon.text =
                    String.format("R$%.2f", it[saloonPos].price)
            }
        }
        scheduleCreated.observe(viewLifecycleOwner) { report ->
            var pix: PixRequest
            with(viewModel) {
                pix = PixRequest(
                    userData.value!!.name,
                    userData.value!!.cpf,
                    String.format("%.2f", report.saloon.saloonPrice).replace(",", "."),
                    report.productName,
                    report.condominiumId
                )
            }
            viewModel.createPixRequest(pix)
        }
        pixCreated.observe(viewLifecycleOwner) { pixResponse ->
            scheduleCreated.value!!.id = pixResponse.id
            scheduleCreated.value!!.imageQrcode = pixResponse.imageQrcode
            scheduleCreated.value!!.qrCode = pixResponse.qrcode


            viewModel.createReport(scheduleCreated.value!!)

            binding.includePayment.tvValueSaloon.text =
                String.format("%.2f", scheduleCreated.value!!.saloon.saloonPrice).replace(",", ".")
            binding.includePayment.tvCode.text = pixResponse.qrcode
            Glide.with(requireActivity())
                .load(pixResponse.imageQrcode)
                .into(binding.includePayment.ivQrCode)
            setLoading(false)
            setupLayout(false)
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

    private fun setLoading(isLoading: Boolean) = with(binding) {
        includeLoading.isVisible = isLoading
    }

    private companion object {
        const val DATE = "date"
    }
}

