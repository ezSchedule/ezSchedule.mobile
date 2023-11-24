package com.ezschedule.admin.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ezschedule.admin.domain.useCase.HistoryUseCase
import com.ezschedule.network.data.ext.toHistory
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.network.domain.presentation.TenantPresentation

class HistoryViewModel(
    private val historyUseCase: HistoryUseCase
) : ViewModel() {

    private var _historyList = MutableLiveData<List<HistoryPresentation>>()
    val historyList: LiveData<List<HistoryPresentation>> = _historyList

    private var _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error


    private var _user = MutableLiveData<TenantPresentation>()
    val user: LiveData<TenantPresentation> = _user

    fun setUser(user: TenantPresentation) = _user.postValue(user)

    fun getAllPaymentsByCondominium(id: Int) {
        historyUseCase("reports-$id")
            .whereEqualTo("condominiumId", id)
            .addSnapshotListener { value, e ->
                when (val response = value?.toHistory()) {
                    null -> _error.postValue(e)

                    else -> when {
                        response.isEmpty() -> _empty.postValue(Unit)

                        else -> _historyList.postValue(response.map { it.toPresentation() })

                    }
                }
            }
    }
}