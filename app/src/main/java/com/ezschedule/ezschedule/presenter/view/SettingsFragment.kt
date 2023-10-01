package com.ezschedule.ezschedule.presenter.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ezschedule.ezschedule.databinding.FragmentSettingsBinding
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.network.domain.data.TenantUpdateRequest
import com.ezschedule.utils.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var activity: MainActivity
    private lateinit var pickUpMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickUpMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                Glide.with(requireContext())
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.includeSettingsProfile.fragSettingsIvIconUser)
            }
        }
    }

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
            setupButtonClickAdmin()
            setScreenPreview(isAdmin = true)
        }
        showUserLayout.observe(viewLifecycleOwner) {
            setScreenPreview(isAdmin = false)
            setupLoading(true)
            viewModel.getTenantInfo(SharedPreferencesManager(requireContext()).getInfo().id)
        }
        tenantSettings.observe(viewLifecycleOwner) {
            setupLayoutProfile()
        }
        condominiumSettings.observe(viewLifecycleOwner) {
            setupLayoutCondominium()
        }
        updateIsComplete.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                "Dados atualizados com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            setupLoading(true)
            viewModel.getTenantInfo(SharedPreferencesManager(requireContext()).getInfo().id)
        }
    }

    private fun setupButtonClick() = with(binding) {
        fragSettingsBtnBack.setOnClickListener {
            findNavController().popBackStack()
            activity.displayLoginItems(isVisible = true)
        }
        fragSettingsBtnSave.setOnClickListener {
            updateTenant()
        }

        includeSettingsProfile.fragSettingsIvIconUser.setOnClickListener {
            pickUpMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun setupLayoutProfile() = with(binding.includeSettingsProfile) {
        viewModel.tenantSettings.value!!.apply {
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
        binding.fragSettingsGroupButtons.isVisible = true
        root.isVisible = true
        setupLoading(false)
    }

    private fun setupLayoutCondominium() = with(binding.includeSettingsCondominium) {
        viewModel.condominiumSettings.value!!.apply {
            fragSettingsTvValueResidents.text = residents.toString()
            fragSettingsTvValueApartments.text = apartments.toString()
            fragSettingsTvValueSalons.text = saloons.toString()
        }
        binding.fragSettingsBtnBack.isVisible = true
        root.isVisible = true
        setupLoading(false)
    }

    private fun setupButtonClickAdmin() = with(binding) {
        includeSettingsHome.fragSettingsBtnProfile.setOnClickListener {
            setupLoading(true)
            includeSettingsHome.root.isVisible = false
            viewModel.getTenantInfo(SharedPreferencesManager(requireContext()).getInfo().id)
        }
        includeSettingsHome.fragSettingsBtnCondominium.setOnClickListener {
            setupLoading(true)
            includeSettingsHome.root.isVisible = false
            viewModel.getCondominiumInfo(SharedPreferencesManager(requireContext()).getInfo().idCondominium)
        }
    }

    private fun setScreenPreview(isAdmin: Boolean) = with(binding) {
        includeSettingsProfile.root.isVisible = !isAdmin
        includeSettingsHome.root.isVisible = isAdmin
    }

    private fun setupLoading(isVisible: Boolean) {
        binding.includeLoading.root.isVisible = isVisible
    }

    private fun updateTenant() = with(binding.includeSettingsProfile) {

        val updatedTenant = TenantUpdateRequest(
            name = fragSettingsEtValueName.text.toString() + " " + fragSettingsEtValueSurname.text.toString(),
            cpf = fragSettingsEtValueCpf.text.toString(),
            apartmentNumber = fragSettingsEtValueApartmentNumber.text.toString().toInt(),
            residentsBlock = fragSettingsEtValueBlock.text.toString(),
            phoneNumber = fragSettingsEtValuePhone.text.toString(),
            email = fragSettingsEtValueEmail.text.toString(),
            image = null
        )

        viewModel.updateTenant(
            SharedPreferencesManager(requireContext()).getInfo().id,
            updatedTenant
        )
    }
}