package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixData
import com.ezschedule.network.data.repository.PixRepository

class UserGetAllPixAttempts(private val repository: PixRepository) {
    suspend operator fun invoke(cpf: String): ResultWrapper<PixData> =
        repository.getAllUserPixAttempts(cpf)

}