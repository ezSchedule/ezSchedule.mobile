package com.ezschedule.admin.presenter.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.ezschedule.admin.databinding.ViewServiceBottomSheetBinding
import com.ezschedule.admin.presenter.adapter.ServicesAdapter
import com.ezschedule.admin.presenter.viewmodel.ServicesViewModel
import com.ezschedule.network.domain.data.ServiceRequest
import com.ezschedule.network.domain.data.ServiceTenant
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

open class ServicesBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: ViewServiceBottomSheetBinding
    private val viewModel by sharedViewModel<ServicesViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        binding = ViewServiceBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setOnShowListener{
            val bottomSheet = it as BottomSheetDialog
            bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        setObserver()
        return dialog
    }

    private fun setObserver() {
        viewModel.serviceCreated.observe(this) {
            Toast.makeText(requireContext(), "Serviço criado com sucesso", Toast.LENGTH_SHORT)
                .show()
            this.dismiss()
        }
        viewModel.tenantsList.observe(this){
            setContent(it)
        }
    }

    fun setContent(tenants:List<TenantServicePresentation>) = with(binding) {
        if (tenants.isNotEmpty()) {
            val adapter = ServicesAdapter(
                tenants = tenants,
                context = requireContext()
            ) { position ->
                btnAddService.setOnClickListener {
                    viewModel.createService(
                        ServiceRequest(
                            serviceName = edtNameNewService.text.toString().trim(),
                            tenant = ServiceTenant(
                                id = tenants[position].id
                            )
                        )
                    )
                }

            }
            fragRvService.adapter = adapter
            setBottomSheetSearchView()
            fragRvService.isVisible = true
            tvNoResidents.isVisible = false
        } else {
            fragRvService.visibility = View.INVISIBLE
            tvNoResidents.isVisible = true
        }
    }


    private fun setBottomSheetSearchView() = with(binding) {
        fragSvService.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val list = viewModel.tenantsList.value!!.filter { service ->
                        service.name.contains(query, true)
                    }
                    fragRvService.adapter =
                        ServicesAdapter(
                            tenants = list,
                            context = requireContext()
                        )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val list = viewModel.tenantsList.value!!.filter { tenants ->
                        tenants.name.contains(newText, true)
                    }
                    fragRvService.adapter = ServicesAdapter(
                        tenants = list,
                        context = requireContext()
                    )
                }
                return false
            }
        })
    }
}