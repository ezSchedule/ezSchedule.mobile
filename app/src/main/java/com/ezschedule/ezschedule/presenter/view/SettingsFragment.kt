package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ezschedule.ezschedule.databinding.FragmentSettingsBinding
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.network.domain.presentation.CondominiumPresentation
import com.ezschedule.utils.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var activity: MainActivity
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActivity()
        viewModel.verifyIsAdmin(SharedPreferencesManager(requireContext()).getInfo().isAdmin)
        setupObservers()
        setupButtonClick()
    }

    private fun setupActivity() {
        activity = requireActivity() as MainActivity
    }

    private fun setupObservers() = with(viewModel) {
        showAdminLayout.observe(viewLifecycleOwner) {
            setupLayoutCondominium()
            setupButtonClickAdmin()
            setScreenPreview(isAdmin = true)
        }
        showUserLayout.observe(viewLifecycleOwner) {
//            setScreenPreview(isAdmin = false)
            setupLoading(true)
            viewModel.getTenantInfo(SharedPreferencesManager(requireContext()).getInfo().id)
        }
        tenantLiveData.observe(viewLifecycleOwner) {
            setupLayoutProfile()
            setScreenPreviewAdmin(isProfile = true, isCondominium = false)
            setupLoading(false)
        }
    }

    private fun setupButtonClick() = with(binding) {
        fragSettingsBtnBack.setOnClickListener {
            findNavController().popBackStack()
            activity.displayLoginItems(isVisible = true)
        }
    }

    private fun setupLayoutProfile() = with(binding.includeSettingsProfile) {
        viewModel.tenantLiveData.value!!.apply {
            image.let {
                Glide.with(requireContext())
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(fragSettingsIvIconUser)
            }
            fragSettingsTvNameUser.text = fullName
            fragSettingsEtValueName.setText(firstName)
            fragSettingsEtValueSurname.setText(surname)
            fragSettingsEtValueCpf.setText(cpf)
            fragSettingsEtValueApartmentNumber.setText(apartmentNumber.toString())
            fragSettingsEtValueBlock.setText(residentsBlock)
            fragSettingsEtValuePhone.setText(phoneNumber)
            fragSettingsEtValueEmail.setText(email)
        }
    }

    private fun setupLayoutCondominium() = with(binding.includeSettingsCondominium) {
        getInfoCondominium().apply {
            fragSettingsEtValueResidents.setText(residentsQuantity.toString())
            fragSettingsEtValueApartments.setText(apartmentsQuantity.toString())
            fragSettingsEtValueSalons.setText(salonsQuantity.toString())
        }
    }

    private fun setupButtonClickAdmin() = with(binding) {
        includeSettingsHome.fragSettingsBtnProfile.setOnClickListener {
            setupLoading(true)
            viewModel.getTenantInfo(SharedPreferencesManager(requireContext()).getInfo().id)
        }
        includeSettingsHome.fragSettingsBtnCondominium.setOnClickListener {
            setupLoading(true)
            setScreenPreviewAdmin(isProfile = false, isCondominium = true)
            setupLoading(false)
        }
    }

    private fun setScreenPreviewAdmin(isProfile: Boolean, isCondominium: Boolean) = with(binding) {
        includeSettingsHome.root.isVisible = false
        fragSettingsGroupButtons.isVisible = true
        includeSettingsProfile.root.isVisible = isProfile
        includeSettingsCondominium.root.isVisible = isCondominium
    }

    private fun setScreenPreview(isAdmin: Boolean) = with(binding) {
        includeSettingsHome.root.isVisible = isAdmin
        fragSettingsGroupButtons.isVisible = !isAdmin
        includeSettingsProfile.root.isVisible = !isAdmin
    }

    private fun setupLoading(isVisible: Boolean) {
        binding.includeLoading.root.isVisible = isVisible
    }

    private fun getInfoCondominium() = CondominiumPresentation(
        residentsQuantity = 223,
        apartmentsQuantity = 67,
        salonsQuantity = 3
    )
}