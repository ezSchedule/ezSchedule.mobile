package com.ezschedule.ezschedule.presenter.view

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
import com.ezschedule.ezschedule.databinding.SettingsBottomSheetBinding
import com.ezschedule.ezschedule.presenter.utils.InputStreamRequestBody
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.network.domain.data.CondominiumRequest
import com.ezschedule.network.domain.data.SaloonRequest
import com.ezschedule.network.domain.data.TenantUpdateRequest
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var bottomSheetBinding: SettingsBottomSheetBinding
    private lateinit var activity: MainActivity
    private lateinit var pickUpMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private val viewModel by viewModel<SettingsViewModel>()
    private lateinit var userInfo: TenantPresentation
    private lateinit var dialog: BottomSheetDialog
    private var isOnInclude = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInfo = SharedPreferencesManager(requireContext()).getInfo()
        dialog = BottomSheetDialog(requireContext())
        setPickUpMedia()
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
        viewModel.verifyIsAdmin(userInfo.isAdmin)
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
            viewModel.getTenantInfo(userInfo.id)
        }

        tenantSettings.observe(viewLifecycleOwner) {
            setupLayoutProfile()
            val updatedUser = viewModel.tenantSettings.value!!

            SharedPreferencesManager(requireContext()).saveInfo(
                TenantPresentation(
                    id = userInfo.id,
                    email = updatedUser.email,
                    name = updatedUser.fullName,
                    image = updatedUser.image ?: "",
                    isAdmin = userInfo.isAdmin,
                    tokenJWT = userInfo.tokenJWT,
                    idCondominium = userInfo.idCondominium
                )
            )
        }

        condominiumSettings.observe(viewLifecycleOwner) {
            setupLayoutCondominium()
        }

        updateIsComplete.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                "Dados atualizados com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            setupLoading(true)
            viewModel.getTenantInfo(userInfo.id)
        }

        viewModel.saloonCreated.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Salão criado com sucesso!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            setupLoading(true)
            viewModel.getCondominiumInfo(userInfo.idCondominium)
        }
    }

    private fun setupButtonClick() = with(binding) {
        fragSettingsBtnBack.setOnClickListener {
            if (isOnInclude && userInfo.isAdmin) {
                fragSettingsBtnSave.isVisible = false
                fragSettingsBtnBack.isVisible = true
                includeSettingsProfile.root.isVisible = false
                includeSettingsCondominium.root.isVisible = false
                includeSettingsHome.root.isVisible = true
                isOnInclude = false
            } else {
                findNavController().popBackStack()
                activity.displayLoginItems(isVisible = true)
            }
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
        isOnInclude = true
        setupLoading(false)
    }

    private fun setupLayoutCondominium() = with(binding.includeSettingsCondominium) {
        viewModel.condominiumSettings.value!!.apply {
            fragSettingsTvValueResidents.text = residents.toString()
            fragSettingsTvValueApartments.text = apartments.toString()
            fragSettingsTvValueSalons.text = saloons.toString()
            fragSettingsTvLabelAddSalons.setOnClickListener {
                setBottomSheet()
            }
        }
        binding.fragSettingsBtnBack.isVisible = true
        root.isVisible = true
        isOnInclude = true
        setupLoading(false)
    }

    private fun setupButtonClickAdmin() = with(binding) {
        includeSettingsHome.fragSettingsBtnProfile.setOnClickListener {
            setupLoading(true)
            includeSettingsHome.root.isVisible = false
            viewModel.getTenantInfo(userInfo.id)
        }
        includeSettingsHome.fragSettingsBtnCondominium.setOnClickListener {
            setupLoading(true)
            includeSettingsHome.root.isVisible = false
            viewModel.getCondominiumInfo(userInfo.idCondominium)
        }
    }

    private fun setScreenPreview(isAdmin: Boolean) = with(binding) {
        includeSettingsProfile.root.isVisible = !isAdmin
        includeSettingsHome.root.isVisible = isAdmin
    }

    private fun setupLoading(isVisible: Boolean) {
        binding.includeLoading.isVisible = isVisible
    }

    private fun updateTenant() = with(binding.includeSettingsProfile) {
        val t = HashMap<String, RequestBody>()
        t["image\"; filename=\""]

        val updatedTenant = TenantUpdateRequest(
            name = fragSettingsEtValueName.text.toString() + " " + fragSettingsEtValueSurname.text.toString(),
            cpf = fragSettingsEtValueCpf.text.toString(),
            apartmentNumber = fragSettingsEtValueApartmentNumber.text.toString().toInt(),
            residentsBlock = fragSettingsEtValueBlock.text.toString(),
            phoneNumber = fragSettingsEtValuePhone.text.toString(),
            email = fragSettingsEtValueEmail.text.toString(),
            image = viewModel.imgHolder.value ?: t
        )

        viewModel.updateTenant(userInfo.id, updatedTenant)
    }

    private fun setBottomSheet() {
        bottomSheetBinding = SettingsBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
        bottomSheetBinding.bsBtn.setOnClickListener {
            viewModel.createSaloon(
                SaloonRequest(
                    name = bottomSheetBinding.etName.text.toString().trim(),
                    price = bottomSheetBinding.etPrice.text.toString().trim().toDouble(),
                    block = bottomSheetBinding.etSaloonNumber.text.toString(),
                    condominium = CondominiumRequest(userInfo.idCondominium)
                )
            )
        }
    }

    private fun setPickUpMedia() {
        pickUpMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let {
                Glide.with(requireContext())
                    .load(it)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.includeSettingsProfile.fragSettingsIvIconUser)

                val params = HashMap<String, RequestBody>()
                val fileName = InputStreamRequestBody.getFileName(requireContext(), it)
                // "file" is your image upload field name
                params["image\"; filename=\"$fileName"] =
                    InputStreamRequestBody(requireContext(), it)
                viewModel.updateImg(params)
            }
        }
    }

}