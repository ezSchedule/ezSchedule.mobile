package com.ezschedule.network.domain.data

import com.google.firebase.Timestamp

data class HistoryData(
    val id: String?,
    val invoiceNumber: String?,
    val category: String?,
    val paymentStatus: String?,
    val productName: String?,
    val condominiumId: Int?,
    val paymentDate: Timestamp?,
    val saloon: SaloonHistoryData,
    val schedule: ScheduleHistoryData,
    val tenant: TenantHistoryData
)

data class SaloonHistoryData(
    val id: Int?,
    val name: String?,
    val saloonPrice: String?,
    val blockEvent: String?
)

data class ScheduleHistoryData(
    val id: Int?,
    val nameEvent: String?,
    val typeEvent: String?,
    val totalNumberGuests: Int?,
    val isCanceled: Boolean?,
    val dateEvent: String?
)

data class TenantHistoryData(
    val id: Int?,
    val name: String?,
    val phoneNumber: String?,
    val unit: String?,
    val apartmentNumber: Long?
)