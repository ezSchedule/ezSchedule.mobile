package com.ezschedule.ezschedule.presenter.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.data.utils.TokenManager
import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.LoginRequest
import com.ezschedule.network.domain.presentation.LoginPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _tenant = MutableLiveData<LoginPresentation>()
    val tenant: LiveData<LoginPresentation> = _tenant

    private val _hasToken = MutableLiveData<Boolean>()
    val hasToken: LiveData<Boolean> = _hasToken

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun login(tenant: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = loginUseCase.execute(tenant)) {
                is ResultWrapper.Success -> {
                    response.content.toLoginPresentation().let {
                        _tenant.postValue(it)
                    }
                }

                is ResultWrapper.Error -> _error.postValue(response.error)
            }
        }
    }

    fun verifySharedPreferences(context: Context) {
        _hasToken.postValue(TokenManager(context).getToken() != null)
    }
}