package com.ezschedule.user.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.R
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.EventItemPresentation
import com.ezschedule.network.domain.useCase.schedule.GetSchedulesCancellations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarUserViewModel(private val useCase: GetSchedulesCancellations) : ViewModel() {
    private var _scheduleList = MutableLiveData<List<EventItemPresentation>>()
    val scheduleList: LiveData<List<EventItemPresentation>> = _scheduleList

    private var _emptyList = MutableLiveData<Unit>()
    val emptyList: LiveData<Unit> = _emptyList

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getEvents(idCondominium: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = useCase.invoke(idCondominium)) {
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

    fun getColorEvent(isCanceled: Boolean) = if (isCanceled) R.color.red else R.color.light_white
}