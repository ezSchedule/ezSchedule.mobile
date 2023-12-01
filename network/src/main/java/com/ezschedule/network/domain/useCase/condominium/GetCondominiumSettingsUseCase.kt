package com.ezschedule.network.domain.useCase.condominium

import com.ezschedule.network.data.repository.CondominiumRepository

class GetCondominiumSettingsUseCase(private val repository: CondominiumRepository) {
    suspend operator fun invoke(id: Int) =
        repository.getCondominiumSettings(id)
}