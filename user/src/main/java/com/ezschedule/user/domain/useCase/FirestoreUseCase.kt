package com.ezschedule.user.domain.useCase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreUseCase {
    operator fun invoke(topic: String) = Firebase.firestore.collection(topic)
}