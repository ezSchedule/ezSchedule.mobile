package com.ezschedule.user.domain.useCase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForumUserUseCase {
    fun execute() = Firebase.firestore.collection("conversations")
}