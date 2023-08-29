package com.ezschedule.network.domain.presentation

data class HistoryPresentation(
    val username:String,
    val saloonName:String,
    val saloonValue:Double,
    val paymentStatus: Boolean = true
)