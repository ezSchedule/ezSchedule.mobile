package com.ezschedule.admin.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.presentation.ChartPresentation
import com.ezschedule.network.domain.useCase.schedule.GetDashboardDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(private val useCase: GetDashboardDataUseCase) : ViewModel() {
    private var _chartData = MutableLiveData<List<ChartPresentation>>()
    val chartData: LiveData<List<ChartPresentation>> = _chartData

    private var _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getChartData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = useCase(id)) {
                is ResultWrapper.Success -> {
                    response.content.toChartsPresentation().let {
                        when {
                            it.isEmpty() -> _empty.postValue(Unit)
                            else -> _chartData.postValue(it)
                        }
                    }
                }

                is ResultWrapper.Error -> _error.postValue(response.error)
            }
        }
    }
}