package com.ezschedule.ezschedule.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.domain.useCase.FindSettingsTenantByIdUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.TenantSettingsPresentation
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val findSettingsTenantByIdUseCase: FindSettingsTenantByIdUseCase
) : ViewModel() {

    private var _showAdminLayout = MutableLiveData<Unit>()
    val showAdminLayout: LiveData<Unit> = _showAdminLayout

    private var _showUserLayout = MutableLiveData<Unit>()
    val showUserLayout: LiveData<Unit> = _showUserLayout

    private var _tenantLiveData = MutableLiveData<TenantSettingsPresentation>()
    val tenantLiveData: LiveData<TenantSettingsPresentation> = _tenantLiveData

    fun verifyIsAdmin(isAdmin: Boolean) {
        if (isAdmin) _showAdminLayout.value = Unit
        else _showUserLayout.value = Unit
    }


    fun getTenantInfo(id: Int) {
        viewModelScope.launch {
            when (val response = findSettingsTenantByIdUseCase(id)) {
                is ResultWrapper.Success -> {
                    response.content.toTenantSettingsPresentation().let {
                        _tenantLiveData.postValue(it)
                    }
                }

                is ResultWrapper.Error -> Log.d("ERROR", "erro na requisição de findUser")
            }
        }
    }

}