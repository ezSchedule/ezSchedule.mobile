package com.ezschedule.user.presenter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.SalonData
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.user.domain.useCase.GetSaloonUseCase
import kotlinx.coroutines.launch

class NewDateViewModel(
    private val getSaloonUseCase: GetSaloonUseCase
) : ViewModel() {

    lateinit var user: TenantPresentation

    private val _saloon = MutableLiveData<List<SalonData>>()
    val saloon: LiveData<List<SalonData>> = _saloon

    private val _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    fun getSaloon() = viewModelScope.launch {
        when (val response = getSaloonUseCase(user.idCondominium)) {

            is ResultWrapper.Success -> response.content.let {
                if (it.isEmpty()) _empty.postValue(Unit) else _saloon.postValue(it)
            }

            is ResultWrapper.Error -> Log.d(
                "Error",
                "erro na requisição de histórico ${response.error}"
            )
        }
    }

}