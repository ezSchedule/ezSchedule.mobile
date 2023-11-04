package com.ezschedule.admin.presenter.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.ezschedule.admin.R
import com.ezschedule.admin.databinding.FragmentServicesBinding
import com.ezschedule.admin.databinding.ViewServiceBottomSheetBinding
import com.ezschedule.admin.presenter.adapter.ServicesAdapter
import com.ezschedule.admin.presenter.viewmodel.ServicesViewModel
import com.ezschedule.network.domain.data.ServiceRequest
import com.ezschedule.network.domain.data.ServiceTenant
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServicesFragment : Fragment() {
    private lateinit var binding: FragmentServicesBinding
    private val viewModel by sharedViewModel<ServicesViewModel>()
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
        setSearchView()
        setupLoading(true)
    }

    private fun setOnClick() = with(binding) {
        fragBtnService.setOnClickListener {
            viewModel.getTenantsList(user.idCondominium)
            showBottomSheet()
        }
    }

    private fun setObservers() {
        viewModel.servicesList.observe(this) {
            setUpContent(it)
            setupLoading(false)
        }
        viewModel.serviceCreated.observe(this){
            viewModel.getServiceList(user.idCondominium)
        }
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
        includeLoading.isVisible = isVisible
    }

    private fun showBottomSheet(){
        val dialog = ServicesBottomSheetFragment()
        dialog.show(parentFragmentManager,dialog.tag)
    }

    private fun setSearchView() = with(binding) {
        fragSvService.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val list = viewModel.servicesList.value!!.filter { service ->
                        service.name.contains(query, true)
                    }
                    fragRvService.adapter =
                        ServicesAdapter(
                            services = list,
                            context = requireContext()
                        )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val list = viewModel.servicesList.value!!.filter { service ->
                        service.name.contains(newText, true)
                    }
                    fragRvService.adapter = ServicesAdapter(
                        services = list,
                        context = requireContext()
                    )
                }
                return false
            }
        })
    }

}