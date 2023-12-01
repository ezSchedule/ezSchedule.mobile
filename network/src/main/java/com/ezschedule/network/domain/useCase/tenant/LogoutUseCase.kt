package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.repository.TenantRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper

class LogoutUseCase(private val repository: TenantRepository) {
    suspend operator fun invoke(email: String): ResultWrapper<Unit> {
        return repository.logout(email)
    }
}