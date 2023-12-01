package com.ezschedule.network.domain.useCase.tenant

import com.ezschedule.network.data.repository.TenantRepository

class LogoutUseCase(private val repository: TenantRepository) {
    suspend operator fun invoke(email: String) = repository.logout(email)
}