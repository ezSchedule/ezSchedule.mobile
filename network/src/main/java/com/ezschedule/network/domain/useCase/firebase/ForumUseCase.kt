package com.ezschedule.network.domain.useCase.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ForumUseCase {
    fun execute(topic:String) = Firebase.firestore.collection(topic)
}