package com.ezschedule.user.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.SchedulesPresentation
import com.ezschedule.user.domain.useCase.ScheduleUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleUserViewModel(private val useCase: ScheduleUserUseCase) : ViewModel() {

    private var _schedules = MutableLiveData<List<SchedulesPresentation>>()
    val schedules: LiveData<List<SchedulesPresentation>> = _schedules

    private var _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getSchedules(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = useCase.execute(id)) {
            is ResultWrapper.Success -> response.content.toSchedulesPresentation().let {
                when {
                    it.isEmpty() -> _empty.postValue(Unit)
                    else -> _schedules.postValue(it)
                }
            }

            is ResultWrapper.Error -> _error.postValue(response.error)
        }
    }
}