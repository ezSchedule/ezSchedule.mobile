package com.ezschedule.user.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.data.SalonData
import com.ezschedule.network.domain.data.ScheduleRequest
import com.ezschedule.network.domain.response.PixResponse
import com.ezschedule.network.domain.response.ReportResponse
import com.ezschedule.network.domain.response.TenantResponse
import com.ezschedule.user.domain.useCase.CreateScheduleUseCase
import com.ezschedule.user.domain.useCase.FirestoreUseCase
import com.ezschedule.user.domain.useCase.GetSaloonUseCase
import com.ezschedule.user.domain.useCase.GetTenantByIdUseCase
import com.ezschedule.user.domain.useCase.PixUseCase
import kotlinx.coroutines.launch

class NewDateViewModel(
    private val getSaloonUseCase: GetSaloonUseCase,
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val getTenantByIdUseCase: GetTenantByIdUseCase,
    private val pixUseCase: PixUseCase,
    private val fireStore: FirestoreUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<TenantResponse>()
    val userData: LiveData<TenantResponse> = _userData

    private val _saloon = MutableLiveData<List<SalonData>>()
    val saloon: LiveData<List<SalonData>> = _saloon

    private val _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private val _scheduleCreated = MutableLiveData<ReportResponse>()
    val scheduleCreated: LiveData<ReportResponse> = _scheduleCreated

    private val _pixCreated = MutableLiveData<PixResponse>()
    val pixCreated: LiveData<PixResponse> = _pixCreated

    fun getUser(id: Int) = viewModelScope.launch {
        when (val response = getTenantByIdUseCase(id)) {

            is ResultWrapper.Success -> response.content.let { _userData.postValue(it) }

            is ResultWrapper.Error ->
                Log.d("Error", "erro na requisição de usuario ${response.error}")
        }
    }

    fun getSaloon() = viewModelScope.launch {
        when (val response = getSaloonUseCase(_userData.value!!.idCondominium)) {

            is ResultWrapper.Success -> response.content.let {
                if (it.isEmpty()) _empty.postValue(Unit) else _saloon.postValue(it)
            }

            is ResultWrapper.Error ->
                Log.d("Error", "erro na requisição de histórico ${response.error}")
        }
    }

    fun createSchedule(schedule: ScheduleRequest) = viewModelScope.launch {
        when (val response = createScheduleUseCase(schedule)) {
            is ResultWrapper.Success -> response.content.let { _scheduleCreated.postValue(it) }

            is ResultWrapper.Error ->
                Log.d("Error", "erro na criação de schedule ${response.error}")
        }
    }

    fun createPixRequest(pix: PixRequest) = viewModelScope.launch {
        when (val response = pixUseCase(pix)) {
            is ResultWrapper.Success -> response.content.let { _pixCreated.postValue(it) }

            is ResultWrapper.Error ->
                Log.d("Error", "erro na criação do pix ${response.error}")
        }
    }

    fun createReport(report: ReportResponse) {
        fireStore("reports-${report.condominiumId}").add(report)
            .addOnSuccessListener { Log.d("FIRESTORE", "Documento criado com sucesso") }
            .addOnFailureListener { Log.d("FIRESTORE", "requisição ao firestore falhou") }
    }

}