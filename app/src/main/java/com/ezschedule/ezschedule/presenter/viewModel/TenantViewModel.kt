package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.domain.useCase.GetTenantsUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.response.TenantsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TenantViewModel(private val tenantsUseCase: GetTenantsUseCase) : ViewModel() {

    val liveData = MutableLiveData<TenantsResponse>()

    fun getTenant() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = tenantsUseCase.execute()) {
                is ResultWrapper.Success -> response.content.let {
                    liveData.postValue(it)
                }

                is ResultWrapper.Error -> Unit
            }
        }
    }
}