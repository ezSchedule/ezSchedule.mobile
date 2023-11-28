package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.response.PixResponse
import com.ezschedule.network.data.repository.PixRepository

class PixUseCase(private val repository: PixRepository) {
    suspend operator fun invoke(pix: PixRequest): ResultWrapper<PixResponse> =
        repository.getPix(pix)

}