package com.ezschedule.user.domain.useCase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForumUserUseCase {
    fun execute(topic:String) = Firebase.firestore.collection(topic)
}