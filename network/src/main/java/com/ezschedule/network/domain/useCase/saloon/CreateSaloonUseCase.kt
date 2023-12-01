package com.ezschedule.network.domain.useCase.saloon

import com.ezschedule.network.data.repository.SaloonRepository
import com.ezschedule.network.domain.data.SaloonRequest

class CreateSaloonUseCase(private val repository: SaloonRepository) {
    suspend operator fun invoke(saloon: SaloonRequest) = repository.createSaloon(saloon)
}