package com.ezschedule.network.domain.presentation

data class PostPresentation(
    val date: String,
    val content: String,
    val type: String,
    val isEdited: Boolean
)
