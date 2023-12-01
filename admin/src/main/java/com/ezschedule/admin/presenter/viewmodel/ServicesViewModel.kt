package com.ezschedule.admin.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.domain.useCase.service.CreateServiceUseCase
import com.ezschedule.network.domain.useCase.service.GetServiceListUseCase
import com.ezschedule.network.domain.useCase.service.GetTenantsListUseCase
import com.ezschedule.network.data.ext.toServicePresentation
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ServiceRequest
import com.ezschedule.network.domain.presentation.ServicePresentation
import com.ezschedule.network.domain.presentation.TenantServicePresentation
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val getTenantsListUseCase: GetTenantsListUseCase,
    private val getServiceListUseCase: GetServiceListUseCase,
    private val createServiceUseCase: CreateServiceUseCase
) : ViewModel() {

    private var _tenantsList = MutableLiveData<List<TenantServicePresentation>>()
    val tenantsList: LiveData<List<TenantServicePresentation>> = _tenantsList

    private var _servicesList = MutableLiveData<List<ServicePresentation>>()
    val servicesList: LiveData<List<ServicePresentation>> = _servicesList

    private var _servicesEmpty = MutableLiveData<Unit>()
    val servicesEmpty: LiveData<Unit> = _servicesEmpty

    private var _serviceCreated = MutableLiveData<Unit>()
    val serviceCreated: LiveData<Unit> = _serviceCreated


    fun getTenantsList(id: Int) = viewModelScope.launch {
        when (val response = getTenantsListUseCase(id)) {
            is ResultWrapper.Success -> _tenantsList.postValue(response.content.map { it.toServicePresentation() })
            is ResultWrapper.Error -> Log.d("ERROR", "${response.error}")
        }
    }

    fun getServiceList(id: Int) = viewModelScope.launch {
        when (val response = getServiceListUseCase(id)) {
            is ResultWrapper.Success -> response.content.toPresentationSimple().let {
                when {
                    it.isEmpty() -> _servicesEmpty.postValue(Unit)
                    else -> _servicesList.postValue(it)
                }
            }

            is ResultWrapper.Error -> Log.d("ERROR", "${response.error}")
        }
    }

    fun createService(service: ServiceRequest) = viewModelScope.launch {
        when (val response = createServiceUseCase(service)) {
            is ResultWrapper.Success -> _serviceCreated.postValue(Unit)
            is ResultWrapper.Error -> Log.d("ERROR", "${response.error}")
        }
    }
}