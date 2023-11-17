package com.ezschedule.user.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.SalonData
import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.domain.data.TenantSettingsData
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.response.TenantResponse
import com.ezschedule.user.domain.useCase.CreateScheduleUseCase
import com.ezschedule.user.domain.useCase.GetSaloonUseCase
import com.ezschedule.user.domain.useCase.GetTenantByIdUseCase
import kotlinx.coroutines.launch

class NewDateViewModel(
    private val getSaloonUseCase: GetSaloonUseCase,
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val getTenantByIdUseCase: GetTenantByIdUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<TenantResponse>()
    val userData: LiveData<TenantResponse> = _userData

    private val _saloon = MutableLiveData<List<SalonData>>()
    val saloon: LiveData<List<SalonData>> = _saloon

    private val _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private val _scheduleCreated = MutableLiveData<Unit>()
    val scheduleCreated: LiveData<Unit> = _scheduleCreated

    fun getUser(id: Int) = viewModelScope.launch {
        when (val response = getTenantByIdUseCase(id)) {

            is ResultWrapper.Success -> _userData.postValue(response.content!!)

            is ResultWrapper.Error -> Log.d(
                "Error",
                "erro na requisição de usuario ${response.error}"
            )
        }
    }

    fun getSaloon() = viewModelScope.launch {
        when (val response = getSaloonUseCase(_userData.value!!.idCondominium)) {

            is ResultWrapper.Success -> response.content.let {
                if (it.isEmpty()) _empty.postValue(Unit) else _saloon.postValue(it)
            }

            is ResultWrapper.Error -> Log.d(
                "Error",
                "erro na requisição de histórico ${response.error}"
            )
        }
    }

    fun createSchedule(schedule: ScheduleRequest) = viewModelScope.launch {
        when (val response = createScheduleUseCase(schedule)) {
            is ResultWrapper.Success -> _scheduleCreated.postValue(Unit)
            is ResultWrapper.Error -> Log.d(
                "Error",
                "erro na criação de schedule ${response.error}"
            )

        }
    }

}