package com.ezschedule.admin.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.admin.domain.useCase.CalendarUseCase
import com.ezschedule.network.R.color.light_white
import com.ezschedule.network.R.color.red
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.EventItemPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(private val useCase: CalendarUseCase) : ViewModel() {
    private var _scheduleList = MutableLiveData<List<EventItemPresentation>>()
    val scheduleList: LiveData<List<EventItemPresentation>> = _scheduleList

    private var _emptyList = MutableLiveData<Unit>()
    val emptyList: LiveData<Unit> = _emptyList

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

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
}