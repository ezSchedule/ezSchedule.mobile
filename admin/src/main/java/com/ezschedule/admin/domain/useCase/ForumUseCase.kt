package com.ezschedule.admin.domain.useCase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForumUseCase {
    fun execute() = Firebase.firestore.collection("conversations")
}