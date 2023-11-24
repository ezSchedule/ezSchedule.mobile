package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.HistoryPresentation
import com.ezschedule.network.domain.presentation.SaloonHistoryPresentation
import com.ezschedule.network.domain.presentation.ScheduleHistoryPresentation
import com.ezschedule.network.domain.presentation.TenantHistoryPresentation
import com.google.firebase.Timestamp

data class HistoryResponse(
    val id: String?,
    val invoiceNumber: String?,
    val paymentStatus: String?,
    val productName: String?,
    val qrCode:String?,
    val imageQrcode:String?,
    val condominiumId: Int?,
    val paymentDate: String?,
    val saloon: SaloonHistoryResponse,
    val schedule: ScheduleHistoryResponse,
    val tenant: TenantHistoryResponse
) {
    fun toPresentation() = HistoryPresentation(
        id = id,
        invoiceNumber = invoiceNumber,
        paymentStatus = paymentStatus,
        productName = productName,
        qrCode = qrCode,
        imageQrcode = imageQrcode,
        condominiumId = condominiumId,
        paymentDate = paymentDate,
        saloon = SaloonHistoryPresentation(
            id = saloon.id,
            name = saloon.name,
            saloonPrice = saloon.saloonPrice,
            blockEvent = saloon.blockEvent
        ),
        schedule = ScheduleHistoryPresentation(
            id = schedule.id,
            nameEvent = schedule.nameEvent,
            typeEvent = schedule.typeEvent,
            totalNumberGuests = schedule.totalNumberGuests,
            isCanceled = schedule.isCanceled,
            dateEvent = schedule.dateEvent
        ),
        tenant = TenantHistoryPresentation(
            id = tenant.id,
            name = tenant.name,
            phoneNumber = tenant.phoneNumber,
            unit = tenant.unit,
            apartmentNumber = tenant.apartmentNumber
        )

    )

}

data class SaloonHistoryResponse(
    val id: Int?,
    val name: String?,
    val saloonPrice: Double?,
    val blockEvent: String?
)

data class ScheduleHistoryResponse(
    val id: Int?,
    val nameEvent: String?,
    val typeEvent: String?,
    val totalNumberGuests: Int?,
    val isCanceled: Boolean?,
    val dateEvent: String
)

data class TenantHistoryResponse(
    val id: Int?,
    val name: String?,
    val phoneNumber: String?,
    val unit: String?,
    val apartmentNumber: Long?
)