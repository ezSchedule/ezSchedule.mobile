package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentServicesBinding
import com.ezschedule.admin.presenter.adapter.ServicesAdapter
import com.ezschedule.admin.presenter.viewmodel.ServicesViewModel
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import com.ezschedule.utils.CustomBottomSheetCallback
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding
    private val viewModel by viewModel<ServicesViewModel>()
    private lateinit var user: TenantPresentation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = SharedPreferencesManager(requireContext()).getInfo()
        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getServiceList(user.idCondominium)
        setOnClick()
        setupLoading(true)
    }

    private fun setOnClick() = with(binding){
        fragBtnService.setOnClickListener {
            viewModel.getTenantsList(user.idCondominium)
        }
    }

    private fun setObservers() {
        viewModel.tenantsList.observe(this) {
            setUpBottomSheetContent(it)
        }
        viewModel.servicesList.observe(this) {
            setUpContent(it)
            setupLoading(false)
        }
    }

    private fun setUpBottomSheetContent(tenants: List<TenantServicePresentation>) =
        with(binding.includeServiceBottomSheet) {
            if (tenants.isNotEmpty()) {
                fragRvService.adapter = ServicesAdapter(
                    tenants = tenants,
                    context = requireContext()
                )
            }else{
                fragRvService.visibility = View.INVISIBLE
                tvNoResidents.isVisible = true
            }
            BottomSheetBehavior.from(this.root).apply {
                addBottomSheetCallback(CustomBottomSheetCallback())
                state = BottomSheetBehavior.STATE_EXPANDED
                peekHeight = 100
            }
            root.isVisible = true
        }

    private fun setUpContent(services: List<ServicePresentation>) = with(binding) {
        if (services.isNotEmpty()) {
            fragRvService.adapter = ServicesAdapter(
                services = services,
                context = requireContext()
            )
            fragRvService.isVisible = true
            fragServicesTvNoServices.isVisible = false
        } else {
            fragRvService.isVisible = false
            fragServicesTvNoServices.isVisible = true
        }
    }

    private fun setupLoading(isVisible: Boolean) = with(binding) {
        includeLoading.root.isVisible = isVisible
    }

}