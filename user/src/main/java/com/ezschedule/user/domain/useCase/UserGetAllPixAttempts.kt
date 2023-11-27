package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixData
import com.ezschedule.user.data.repository.PixUserRepository

class UserGetAllPixAttempts(private val repository: PixUserRepository) {
    suspend operator fun invoke(cpf: String): ResultWrapper<PixData> =
        repository.getAllPixAttempts(cpf)

}