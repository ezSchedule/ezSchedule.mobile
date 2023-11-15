package com.ezschedule.network.domain.data

import com.google.firebase.Timestamp

data class PostData(
    val dateTimePost: Timestamp?,
    val idCondominium: Long?,
    val isEdited: Boolean?,
    val textContent: String?,
    val typeMessage: String?
)