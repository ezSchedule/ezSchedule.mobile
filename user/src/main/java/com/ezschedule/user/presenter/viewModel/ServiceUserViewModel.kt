package com.ezschedule.user.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.ServiceUserPresentation
import com.ezschedule.network.domain.useCase.service.GetServiceListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiceUserViewModel(private val useCase: GetServiceListUseCase) : ViewModel() {
    private val _services = MutableLiveData<List<ServiceUserPresentation>>()
    val services: LiveData<List<ServiceUserPresentation>> = _services

    private val _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getServices(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = useCase(id)) {
            is ResultWrapper.Success -> response.content.toPresentation().let {
                when {
                    it.isEmpty() -> _empty.postValue(Unit)
                    else -> _services.postValue(it)
                }
            }

            is ResultWrapper.Error -> _error.postValue(response.error)
        }
    }
}