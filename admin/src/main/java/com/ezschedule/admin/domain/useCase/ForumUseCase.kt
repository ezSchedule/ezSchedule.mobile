package com.ezschedule.admin.domain.useCase

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForumUseCase {
    fun execute(id: Int) =
        Firebase.firestore.collection("conversations").whereEqualTo(POST_ID, id)
            .orderBy("date_time_post", Query.Direction.ASCENDING)

    companion object {
        private const val POST_ID = "id_condominium"
    }
}