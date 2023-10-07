package com.ezschedule.ezschedule.domain.useCase

import com.ezschedule.ezschedule.data.repository.SaloonRepository
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.SaloonRequest

class CreateSaloonUseCase(private val repository: SaloonRepository) {
    suspend operator fun invoke(saloon: SaloonRequest): ResultWrapper<Unit> =
        repository.createSaloon(saloon)
}