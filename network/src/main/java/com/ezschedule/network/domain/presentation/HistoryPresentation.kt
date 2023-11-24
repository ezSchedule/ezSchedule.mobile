package com.ezschedule.network.domain.presentation

data class HistoryPresentation(
    val id: String?,
    var invoiceNumber: String?,
    var paymentStatus: String?,
    val productName: String?,
    val qrCode:String?,
    val imageQrcode:String?,
    val condominiumId: Int?,
    var paymentDate: String?,
    val saloon: SaloonHistoryPresentation,
    val schedule: ScheduleHistoryPresentation,
    val tenant: TenantHistoryPresentation
)

data class SaloonHistoryPresentation(
    val id: Int?,
    val name: String?,
    val saloonPrice: Double?,
    val blockEvent: String?
)

data class ScheduleHistoryPresentation(
    val id: Int?,
    val nameEvent: String?,
    val typeEvent: String?,
    val totalNumberGuests: Int?,
    val isCanceled: Boolean?,
    val dateEvent: String?
)

data class TenantHistoryPresentation(
    val id: Int?,
    val name: String?,
    val phoneNumber: String?,
    val unit: String?,
    val apartmentNumber: Long?
)