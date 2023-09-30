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
import com.ezschedule.ezschedule.presenter.utils.TokenManager
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.network.domain.presentation.CondominiumPresentation
import com.ezschedule.network.domain.presentation.SettingsPresentation
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
        viewModel.verifyIsAdmin(TokenManager(requireContext()).getInfo().isAdmin)
        setupObservers()
        setupButtonClick()
        setupLayoutProfile()
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
            setScreenPreview(isAdmin = false)
        }
    }

    private fun setupButtonClick() = with(binding) {
        fragSettingsBtnBack.setOnClickListener {
            findNavController().popBackStack()
            activity.displayLoginItems(isVisible = true)
        }
    }

    private fun setupLayoutProfile() = with(binding.includeSettingsProfile) {
        Glide.with(requireContext())
            .load(getInfoTenant().image)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(fragSettingsIvIconUser)
        fragSettingsTvNameUser.text = getInfoTenant().name
        fragSettingsEtValueName.setText(getInfoTenant().name)
        fragSettingsEtValueSurname.setText(getInfoTenant().name)
        fragSettingsEtValueCpf.setText(getInfoTenant().cpf)
        fragSettingsEtValueApartmentNumber.setText(getInfoTenant().apartmentNumber.toString())
        fragSettingsEtValueBlock.setText(getInfoTenant().residentsBlock)
        fragSettingsEtValuePhone.setText(getInfoTenant().phoneNumber)
        fragSettingsEtValueEmail.setText(getInfoTenant().email)
    }

    private fun setupLayoutCondominium() = with(binding.includeSettingsCondominium) {
        fragSettingsEtValueResidents.setText(getInfoCondominium().residentsQuantity.toString())
        fragSettingsEtValueApartments.setText(getInfoCondominium().apartmentsQuantity.toString())
        fragSettingsEtValueSalons.setText(getInfoCondominium().salonsQuantity.toString())
    }

    private fun setupButtonClickAdmin() = with(binding) {
        includeSettingsHome.fragSettingsBtnProfile.setOnClickListener {
            setupLoading(true)
            setScreenPreviewAdmin(isProfile = true, isCondominium = false)
            setupLoading(false)
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

    private fun getInfoTenant() = SettingsPresentation(
        name = "Endryl Fiorotti",
        email = "endryl@gmail.com",
        cpf = "000.000.000-00",
        residentsBlock = "1A",
        apartmentNumber = 44,
        phoneNumber = "(11) 99999-9999",
        image = "https://imgv3.fotor.com/images/gallery/Realistic-Male-Profile-Picture.jpg"
    )

    private fun getInfoCondominium() = CondominiumPresentation(
        residentsQuantity = 223,
        apartmentsQuantity = 67,
        salonsQuantity = 3
    )
}