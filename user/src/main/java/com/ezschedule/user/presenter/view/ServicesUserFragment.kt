package com.ezschedule.user.presenter.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ezschedule.network.domain.presentation.ServiceUserPresentation
import com.ezschedule.user.presenter.adapter.ServicesAdapterUser
import com.ezschedule.user.presenter.viewModel.ServiceUserViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sptech.user.databinding.FragmentServicesUserBinding
import com.sptech.user.databinding.ViewServiceUserBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ServicesUserFragment : Fragment() {
    private lateinit var binding: FragmentServicesUserBinding
    private lateinit var bottomSheetBinding: ViewServiceUserBottomSheetBinding
    private lateinit var dialog: BottomSheetDialog
    private val viewModel by viewModel<ServiceUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = BottomSheetDialog(requireContext())
        viewModel.getServices(SharedPreferencesManager(requireContext()).getInfo().idCondominium)
        setupLoading(true)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        services.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
            setupEmpty(false)
            setupLoading(false)
        }
        empty.observe(viewLifecycleOwner) {
            setupLoading(false)
            setupEmpty(true)
        }
    }

    private fun setupRecyclerView(services: List<ServiceUserPresentation>) {
        val adapter = ServicesAdapterUser(services, requireContext()) {
            setupBottomSheet()
            displayUserBottomSheet(it)
        }
        binding.rvService.adapter = adapter
        setupSearchField(adapter)
    }

    private fun setupBottomSheet() {
        bottomSheetBinding = ViewServiceUserBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }

    private fun setupSearchField(adapter: ServicesAdapterUser) {
        binding.svService.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun displayUserBottomSheet(serviceData: ServiceUserPresentation) =
        with(bottomSheetBinding) {
            Glide.with(requireContext())
                .load(serviceData.image)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(ivIconUser)
            tvNameUser.text = serviceData.userName
            etValueApartmentNumber.setText(serviceData.apartmentNumber)
            etValueBlock.setText(serviceData.block)
            etValuePhone.setText(serviceData.phone)
            ivIconWhatsapp.setOnClickListener {
                openWhatsapp(serviceData)
            }
        }

    private fun openWhatsapp(serviceData: ServiceUserPresentation) {
        try {
            requireContext().startActivity(
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data =
                        Uri.parse("https://api.whatsapp.com/send?phone=${serviceData.phone}")
                    setPackage("com.whatsapp")
                }
            )
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            requireContext().startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=pt_BR")
                setPackage("com.android.vending")
            })
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        includeLoading.isVisible = isLoading
        gpLayout.isVisible = isLoading.not()
    }

    private fun setupEmpty(isEmpty: Boolean) = with(binding) {
        tvEmpty.isVisible = isEmpty
        gpLayout.isVisible = isEmpty.not()
    }
}