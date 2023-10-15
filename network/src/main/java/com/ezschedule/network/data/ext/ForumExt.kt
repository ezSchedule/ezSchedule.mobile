package com.ezschedule.network.data.ext

import com.ezschedule.network.domain.data.PostData
import com.ezschedule.network.domain.response.PostResponse
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.Date

fun QuerySnapshot.toObject() = this.map {
    it.toObject()
}.toResponse()

fun QueryDocumentSnapshot.toObject() = PostData(
    dateTimePost = this.getTimestamp("date_time_post"),
    idCondominium = this.getLong("id_condominium"),
    isEdited = this.getBoolean("is_edited"),
    textContent = this.getString("text_content"),
    typeMessage = this.getString("type_message")
)

fun List<PostData>.toResponse() = this.map {
    it.toResponse()
}

fun PostData.toResponse() = PostResponse(
    date = dateTimePost ?: Timestamp(Date()),
    isEdited = isEdited ?: false,
    content = textContent ?: "",
    type = typeMessage ?: ""
)