package com.ezschedule.admin.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.admin.domain.useCase.CalendarUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.response.ScheduleResponse
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val calendarUseCase: CalendarUseCase
) : ViewModel() {

    private var _scheduleList = MutableLiveData<List<ScheduleResponse>>()
    val scheduleList: LiveData<List<ScheduleResponse>> = _scheduleList

    private var _user = MutableLiveData<TenantPresentation>()
    val user: LiveData<TenantPresentation> = _user

    fun setUser(user:TenantPresentation) = _user.postValue(user)

    fun getUserPayments() = viewModelScope.launch {
        when (val response = calendarUseCase.execute(user.value!!.idCondominium)) {
            is ResultWrapper.Success -> {
                _scheduleList.postValue(response.content.schedules)
            }

            is ResultWrapper.Error -> Log.d("ERROR", "${response.error}")
        }
    }

}