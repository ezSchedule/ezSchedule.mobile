package com.ezschedule.network.domain.useCase.pix

import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.network.data.repository.PixRepository
import com.ezschedule.network.domain.data.PixRequest
import com.ezschedule.network.domain.response.PixResponse

class PixUseCase(private val repository: PixRepository) {
    suspend operator fun invoke(pix: PixRequest): ResultWrapper<PixResponse> {
        return repository.getPix(pix)
    }
}