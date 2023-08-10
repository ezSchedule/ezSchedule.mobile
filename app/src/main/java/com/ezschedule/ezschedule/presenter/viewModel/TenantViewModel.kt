package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.presentation.TenantPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TenantViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _tenant = MutableLiveData<TenantPresentation>()
    val tenant: LiveData<TenantPresentation> = _tenant

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun login(tenant: TenantRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = loginUseCase.execute(tenant)) {
                is ResultWrapper.Success -> {
                    response.content.toTenantPresentation().let {
                        _tenant.postValue(it)
                    }
                }

                is ResultWrapper.Error -> _error.postValue(response.error)
            }
        }
    }
}