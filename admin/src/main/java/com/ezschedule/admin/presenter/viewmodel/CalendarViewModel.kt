package com.ezschedule.admin.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.domain.useCase.schedule.CalendarUseCase
import com.ezschedule.network.R.color.light_white
import com.ezschedule.network.R.color.red
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.ScheduleData
import com.ezschedule.network.domain.presentation.EventItemPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(private val useCase: CalendarUseCase) : ViewModel() {
    private var _scheduleList = MutableLiveData<List<EventItemPresentation>>()
    val scheduleList: LiveData<List<EventItemPresentation>> = _scheduleList

    private var _successfulCancellation = MutableLiveData<Unit>()
    val successfulCancellation: LiveData<Unit> = _successfulCancellation

    private var _emptyList = MutableLiveData<Unit>()
    val emptyList: LiveData<Unit> = _emptyList

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    private var _notBlank = MutableLiveData<Unit>()
    val notBlank: LiveData<Unit> = _notBlank

    private var _blank = MutableLiveData<Unit>()
    val blank: LiveData<Unit> = _blank

    fun getEvents(idCondominium: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = useCase.execute(idCondominium)) {
                is ResultWrapper.Success -> {
                    response.content.toEventsPresentation().let {
                        when {
                            it.isEmpty() -> _emptyList.postValue(Unit)
                            else -> _scheduleList.postValue(it)
                        }
                    }
                }

                is ResultWrapper.Error -> _error.postValue(response.error)
            }
        }
    }

    fun getColorEvent(isCanceled: Boolean) = if (isCanceled) red else light_white

    fun verifyField(text: String, date: String, id: Int) {
        if (text.isBlank()) _blank.value = Unit
        else {
            _notBlank.value = Unit
            sendCancelDay(ScheduleData.cancelDay(reason = text, date = date, idTenant = id))
        }
    }

    private fun sendCancelDay(schedule: ScheduleData) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = useCase.cancelDate(schedule)) {
                is ResultWrapper.Success -> _successfulCancellation.postValue(Unit)

                is ResultWrapper.Error -> _error.postValue(response.error)
            }
        }
    }
}