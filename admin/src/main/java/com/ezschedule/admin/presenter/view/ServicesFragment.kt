package com.ezschedule.admin.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ezschedule.admin.databinding.FragmentServicesBinding
import com.ezschedule.admin.presenter.adapter.AddTenantsServicesAdapter
import com.ezschedule.admin.presenter.adapter.ServicesAdapter
import com.ezschedule.admin.presenter.viewmodel.ServicesViewModel
import com.ezschedule.network.domain.data.ServiceRequest
import com.ezschedule.network.domain.data.ServiceTenant
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import com.ezschedule.utils.SharedPreferencesManager
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
        setSearchView()
        setupLoading(true)
    }

    private fun setOnClick() = with(binding) {
        fragBtnService.setOnClickListener {
            viewModel.getTenantsList(user.idCondominium)
        }
    }

    private fun setObservers() {
        viewModel.tenantsList.observe(this) {
            showViewAddService(it)
        }

        viewModel.servicesList.observe(this) {
            setUpContent(it)
            setupLoading(false)
        }
        viewModel.serviceCreated.observe(this) {
            Toast.makeText(requireContext(), "Serviço criado com sucesso", Toast.LENGTH_SHORT)
                .show()
            viewModel.getServiceList(user.idCondominium)
            viewVisibility(true)
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

    private fun showViewAddService(tenants: List<TenantServicePresentation>) =
        with(binding.includeAddService) {
            viewVisibility(false)
            if (tenants.isNotEmpty()) {
                val adapter = addAdapter(tenants)
                fragServicesBtnBack.setOnClickListener {
                    viewVisibility(true)
                }
                fragRvService.adapter = adapter
                setViewAddService()
                fragRvService.isVisible = true
                tvNoResidents.isVisible = false
            } else {
                fragRvService.visibility = View.INVISIBLE
                tvNoResidents.isVisible = true
            }
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

    private fun setViewAddService() = with(binding.includeAddService) {
        fragSvService.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val list = viewModel.tenantsList.value!!.filter { service ->
                        service.name.contains(query, true)
                    }
                    fragRvService.adapter = addAdapter(list)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val list = viewModel.tenantsList.value!!.filter { tenants ->
                        tenants.name.contains(newText, true)
                    }
                    fragRvService.adapter = addAdapter(list)
                }
                return false
            }
        })
    }

    private fun viewVisibility(isVisible: Boolean) {
        binding.fragClService.isVisible = isVisible
        binding.includeAddService.root.isVisible = !isVisible
    }

    private fun addAdapter(tenants: List<TenantServicePresentation>): AddTenantsServicesAdapter {
        return AddTenantsServicesAdapter(
            tenants = tenants,
            context = requireContext()
        ) { position ->
            binding.includeAddService.btnAddService.setOnClickListener {
                viewModel.createService(
                    ServiceRequest(
                        serviceName = binding.includeAddService.edtNameNewService.text.toString()
                            .trim(),
                        tenant = ServiceTenant(
                            id = tenants[position].id
                        )
                    )
                )
            }
        }
    }

}