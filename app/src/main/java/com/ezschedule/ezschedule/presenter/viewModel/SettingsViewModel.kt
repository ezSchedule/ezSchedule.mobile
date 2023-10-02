package com.ezschedule.ezschedule.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.domain.useCase.GetCondominiumSettingsUseCase
import com.ezschedule.ezschedule.domain.useCase.GetTenantSettingsUseCase
import com.ezschedule.ezschedule.domain.useCase.UpdateTenantSettingsUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.CondominiumSettingsData
import com.ezschedule.network.domain.data.TenantUpdateRequest
import com.ezschedule.network.domain.presentation.TenantSettingsPresentation
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SettingsViewModel(
    private val getTenantSettingsUseCase: GetTenantSettingsUseCase,
    private val getCondominiumSettingsUseCase: GetCondominiumSettingsUseCase,
    private val updateTenantSettingsUseCase: UpdateTenantSettingsUseCase
) : ViewModel() {

    private var _showAdminLayout = MutableLiveData<Unit>()
    val showAdminLayout: LiveData<Unit> = _showAdminLayout

    private var _showUserLayout = MutableLiveData<Unit>()
    val showUserLayout: LiveData<Unit> = _showUserLayout

    private var _tenantSettings = MutableLiveData<TenantSettingsPresentation>()
    val tenantSettings: LiveData<TenantSettingsPresentation> = _tenantSettings

    private var _condominiumSettings = MutableLiveData<CondominiumSettingsData>()
    val condominiumSettings: LiveData<CondominiumSettingsData> = _condominiumSettings

    private var _updateIsComplete = MutableLiveData<Unit>()
    val updateIsComplete: LiveData<Unit> = _updateIsComplete


    private var _imgHolder = MutableLiveData<Map<String,RequestBody>>()
    val imgHolder: LiveData<Map<String,RequestBody>> = _imgHolder

    private var updatedImg = false


    fun verifyIsAdmin(isAdmin: Boolean) {
        if (isAdmin) _showAdminLayout.value = Unit
        else _showUserLayout.value = Unit
    }

    fun getTenantInfo(id: Int) {
        viewModelScope.launch {
            when (val response = getTenantSettingsUseCase(id)) {
                is ResultWrapper.Success -> {
                    response.content.toTenantSettingsPresentation().let {
                        _tenantSettings.postValue(it)
                    }
                }

                is ResultWrapper.Error -> Log.d("ERROR", "erro na requisição de getUserSettings")
            }
        }
    }

    fun getCondominiumInfo(id: Int) {
        viewModelScope.launch {
            when (val response = getCondominiumSettingsUseCase(id)) {
                is ResultWrapper.Success -> {
                    response.content.let {
                        _condominiumSettings.postValue(it)
                    }
                }

                is ResultWrapper.Error -> Log.d(
                    "ERROR",
                    "erro na requisição de getCondominiumSettings"
                )
            }
        }
    }

    fun updateTenant(id: Int, tenantUpdateRequest: TenantUpdateRequest) {
        viewModelScope.launch {
            when (updateTenantSettingsUseCase(id, tenantUpdateRequest)) {
                is ResultWrapper.Success -> _updateIsComplete.value = Unit
                is ResultWrapper.Error -> Log.d("ERROR", "erro na requisição de update")
            }
        }
    }

    fun updateImg(img: Map<String,RequestBody>) {
        _imgHolder.postValue(img)
    }

}