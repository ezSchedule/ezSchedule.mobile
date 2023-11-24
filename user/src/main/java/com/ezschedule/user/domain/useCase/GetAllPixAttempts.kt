package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixData
import com.ezschedule.network.domain.response.PixResponse
import com.ezschedule.user.data.repository.PixRepository

class GetAllPixAttempts(private val repository: PixRepository) {
    suspend operator fun invoke(cpf: String): ResultWrapper<PixData> =
        repository.getAllPixAttempts(cpf)

}