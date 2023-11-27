package com.ezschedule.user.domain.useCase

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.response.PixResponse
import com.ezschedule.user.data.repository.PixUserRepository

class PixUseCase(private val repository: PixUserRepository) {
    suspend operator fun invoke(pix: PixRequest): ResultWrapper<PixResponse> =
        repository.getPix(pix)

}