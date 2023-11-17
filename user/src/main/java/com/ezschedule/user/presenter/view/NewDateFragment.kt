package com.ezschedule.user.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.user.presenter.viewModel.NewDateViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.sptech.user.R
import com.sptech.user.databinding.FragmentNewDateBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewDateFragment : Fragment() {
    private lateinit var binding: FragmentNewDateBinding
    private val viewModel by viewModel<NewDateViewModel>()
    private lateinit var date: String


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
        arguments?.getString("DATE")?.let {
            date = it
        }
        viewModel.user = SharedPreferencesManager(requireContext()).getInfo()
        viewModel.getSaloon()
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
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

    private fun setObservers() = with(viewModel) {
        saloon.observe(viewLifecycleOwner) {
            val saloonNameList = arrayListOf<String>()
            it.forEach { saloonData -> saloonNameList.add(saloonData.name) }
            val adapter = ArrayAdapter(
                requireContext(), R.layout.adapter_auto_complete_new_date, saloonNameList
            )
            binding.includeInfo.acSaloon.setAdapter(adapter)
            binding.includeInfo.acSaloon.setOnItemClickListener { adapterView, view, i, l ->
                binding.includeInfo.tvValueSaloon.text = String.format("R$%.2f", it[i].price)
            }
        }

        empty.observe(viewLifecycleOwner) {

        }

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
}