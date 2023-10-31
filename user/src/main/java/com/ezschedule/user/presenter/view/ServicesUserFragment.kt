package com.ezschedule.user.presenter.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.user.presenter.adapter.ServicesAdapterUser
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sptech.user.databinding.FragmentServicesUserBinding
import com.sptech.user.databinding.ViewServiceUserBottomSheetBinding

class ServicesUserFragment : Fragment() {
    private lateinit var binding: FragmentServicesUserBinding
    private lateinit var bottomSheetBinding: ViewServiceUserBottomSheetBinding
    private lateinit var dialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = BottomSheetDialog(requireContext())
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = ServicesAdapterUser(getData(), requireContext()) {
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

    private fun displayUserBottomSheet(serviceData: ServicePresentation) =
        with(bottomSheetBinding) {
            Glide.with(requireContext())
                .load(serviceData.image)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(ivIconUser)
            tvNameUser.text = serviceData.userName
            etValueApartmentNumber.setText("12")
            etValueBlock.setText("1A")
            etValuePhone.setText(serviceData.userNumber)
            ivIconWhatsapp.setOnClickListener {
                openWhatsapp(serviceData)
            }
        }

    private fun openWhatsapp(serviceData: ServicePresentation) {
        try {
            requireContext().startActivity(
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data =
                        Uri.parse("https://api.whatsapp.com/send?phone=${serviceData.userNumber}")
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

    private fun getData() = listOf(
        ServicePresentation(
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFPX3If-y5nLhFyA3Qg5CDZCoxkJR6gAbngw&usqp=CAU",
            name = "Desenvolvedor",
            userName = "Vinícius Almeida",
            userNumber = "95780-6515"
        ),
        ServicePresentation(
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFPX3If-y5nLhFyA3Qg5CDZCoxkJR6gAbngw&usqp=CAU",
            name = "UI/UX",
            userName = "Charles",
            userNumber = "95881-9026"
        ),
        ServicePresentation(
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFPX3If-y5nLhFyA3Qg5CDZCoxkJR6gAbngw&usqp=CAU",
            name = "Carpinteiro",
            userName = "Shu",
            userNumber = "95163-1129"
        ),
        ServicePresentation(
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFPX3If-y5nLhFyA3Qg5CDZCoxkJR6gAbngw&usqp=CAU",
            name = "QA",
            userName = "Andrew",
            userNumber = "96176-9026"
        ),
        ServicePresentation(
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFPX3If-y5nLhFyA3Qg5CDZCoxkJR6gAbngw&usqp=CAU",
            name = "DevOps",
            userName = "Greg",
            userNumber = "95655-8187"
        ),
        ServicePresentation(
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFPX3If-y5nLhFyA3Qg5CDZCoxkJR6gAbngw&usqp=CAU",
            name = "Desenvolvedor Android",
            userName = "Endryl",
            userNumber = "94251-8747"
        )
    )
}