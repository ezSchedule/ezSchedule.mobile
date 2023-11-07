package com.ezschedule.network.domain.response

data class ServicesResponse(
    val results: List<ServiceResponse>
) {
    fun toPresentationSimple() = results.map {
        it.toPresentation()
    }

    fun toPresentation() = results.map {
        it.toUserPresentation()
    }
}