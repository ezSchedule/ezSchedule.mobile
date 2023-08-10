package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.LoginRepository
import com.ezschedule.network.domain.data.LoginRequest
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.response.LoginResponse

class LoginUseCase(private val repository: LoginRepository) {
    suspend fun execute(tenant: LoginRequest): ResultWrapper<LoginResponse> {
        return repository.login(tenant)
    }
}