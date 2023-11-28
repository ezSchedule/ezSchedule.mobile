package com.ezschedule.admin.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.network.domain.useCase.pix.GetAllPixAttempts
import com.ezschedule.network.domain.useCase.firebase.HistoryUseCase
import com.ezschedule.network.data.ext.toHistory
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixCobData
import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyUseCase: HistoryUseCase,
    private val getAllPixAttempts: GetAllPixAttempts,
) : ViewModel() {

    private var _historyList = MutableLiveData<List<HistoryPresentation>>()
    val historyList: LiveData<List<HistoryPresentation>> = _historyList

    private var _pixAttempts = MutableLiveData<List<PixCobData>>()
    val pixAttempts: LiveData<List<PixCobData>> = _pixAttempts

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
            .orderBy("paymentDate",Query.Direction.DESCENDING)
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

    fun getAllPixAttemps() = viewModelScope.launch {
        when (val response = getAllPixAttempts()) {
            is ResultWrapper.Success -> _pixAttempts.postValue(response.content.cobs)
            is ResultWrapper.Error -> Log.d(
                "ERROR",
                "erro na requisição de todos pagamentos ${response.error}"
            )

        }
    }

    fun updateHistoryWPixInfo() {
        val historyList = historyList.value?.filter { it.paymentStatus.equals("ATIVO") }
        val pixList = pixAttempts.value?.filter { it.status == "CONCLUIDA" }

        historyList?.forEach { history ->
            pixList?.forEach { pix ->
                if (history.id.equals(pix.txid)) {
                    history.paymentStatus = "PAGO"
                    history.paymentDate = pix.pix?.get(0)?.horario
                    history.invoiceNumber = pix.pix?.get(0)?.endToEndId
                    historyUseCase("reports-${user.value?.idCondominium}").document(pix.txid)
                        .set(history)
                    Log.d("PIX","${pix.txid} foi atualizado")
                }
            }
        }
    }
}