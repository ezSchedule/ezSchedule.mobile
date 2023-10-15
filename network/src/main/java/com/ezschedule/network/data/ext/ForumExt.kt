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
    dateTimePost = this.getTimestamp("dateTimePost"),
    idCondominium = this.getLong("idCondominium"),
    isEdited = this.getBoolean("isEdited"),
    textContent = this.getString("textContent"),
    typeMessage = this.getString("typeMessage")
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