package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.R
import com.ezschedule.ezschedule.presenter.utils.isValidEmail
import com.ezschedule.network.data.network.exception.ClientException
import com.ezschedule.network.data.network.exception.ServerException
import com.ezschedule.network.data.network.exception.UnknownResponseException
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.TenantRequest
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.network.domain.useCase.tenant.LoginUseCase
import com.ezschedule.utils.ResourceWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TenantViewModel(
    private val loginUseCase: LoginUseCase,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {
    private val _setErrorEmail = MutableLiveData<String>()
    val setErrorEmail: LiveData<String> = _setErrorEmail

    private val _setStatusButtonLogin = MutableLiveData<Boolean>()
    val setStatusButtonLogin: LiveData<Boolean> = _setStatusButtonLogin

    private val _loginSuccess = MutableLiveData<TenantPresentation>()
    val loginSuccess: LiveData<TenantPresentation> = _loginSuccess

    private var _setLocation = MutableLiveData<Pair<Int, Int>>()
    val setLocationAndMenu: LiveData<Pair<Int, Int>> = _setLocation

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var isEmailFilled: Boolean = false
    private var isPasswordFilled: Boolean = false

    fun changeEmail(email: String) {
        validationEmail(email)
        isEmailFilled = email.isNotEmpty() && email.isValidEmail()
        setStatusButtonLogin()
    }

    fun changePassword(password: String) {
        isPasswordFilled = password.isNotEmpty()
        setStatusButtonLogin()
    }

    fun login(tenant: TenantRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = loginUseCase(tenant)) {
                is ResultWrapper.Success -> {
                    response.content.toTenantPresentation().let {
                        _loginSuccess.postValue(it)
                    }
                }

                is ResultWrapper.Error -> {
                    when (response.error) {
                        is ClientException -> _error.postValue(
                            resourceWrapper.getString(com.ezschedule.network.R.string.frag_login_client_exception)
                        )

                        is ServerException -> _error.postValue(
                            resourceWrapper.getString(com.ezschedule.network.R.string.frag_login_server_exception)
                        )

                        is UnknownResponseException -> _error.postValue(
                            resourceWrapper.getString(com.ezschedule.network.R.string.frag_login_unknown_exception)
                        )
                    }
                }
            }
        }
    }

    fun validateIsAdmin(admin: Boolean) {
        if (admin) _setLocation.value = Pair(R.id.action_to_calendar, R.menu.menu_admin)
        else _setLocation.value = Pair(R.id.action_to_calendar_user, R.menu.menu_user)
    }

    private fun validationEmail(email: String) {
        if (email.isValidEmail()) _setErrorEmail.postValue(EMPTY_STRING)
        else _setErrorEmail.postValue(resourceWrapper.getString(com.ezschedule.network.R.string.frag_login_edt_email_error))
    }

    private fun setStatusButtonLogin() {
        _setStatusButtonLogin.postValue(isEmailFilled && isPasswordFilled)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}