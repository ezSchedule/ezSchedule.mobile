package com.ezschedule.network.domain.response

import com.ezschedule.network.domain.presentation.PostPresentation
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class PostResponse(
    val date: Timestamp,
    val isEdited: Boolean,
    val content: String,
    val type: String
) {
    private val dateFormat = SimpleDateFormat("d 'de' MMMM", Locale.getDefault())

    fun toPresentation() = PostPresentation(
        date = dateFormat.format(date.toDate()),
        content = content,
        type = type,
        isEdited = isEdited
    )
}