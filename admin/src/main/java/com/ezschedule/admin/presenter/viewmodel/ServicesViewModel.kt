package com.ezschedule.admin.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.admin.domain.useCase.GetServiceListUseCase
import com.ezschedule.admin.domain.useCase.GetTenantsListUseCase
import com.ezschedule.network.data.ext.toServicePresentation
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val getTenantsListUseCase: GetTenantsListUseCase,
    private val getServiceListUseCase: GetServiceListUseCase
) : ViewModel() {

    private var _tenantsList = MutableLiveData<List<TenantServicePresentation>>()
    val tenantsList: LiveData<List<TenantServicePresentation>> = _tenantsList

    private var _servicesList = MutableLiveData<List<ServicePresentation>>()
    val servicesList: LiveData<List<ServicePresentation>> = _servicesList


    fun getTenantsList(id: Int) = viewModelScope.launch {
        when (val response = getTenantsListUseCase(id)) {
            is ResultWrapper.Success -> _tenantsList.postValue(response.content.map { it.toServicePresentation() } )
            is ResultWrapper.Error -> Log.d("ERROR", "${response.error}")
        }
    }

    fun getServiceList(id: Int) = viewModelScope.launch {
        when (val response = getServiceListUseCase(id)) {
            is ResultWrapper.Success -> _servicesList.postValue(response.content.map { it.toPresentation() })
            is ResultWrapper.Error -> Log.d("ERROR", "${response.error}")
        }
    }


}