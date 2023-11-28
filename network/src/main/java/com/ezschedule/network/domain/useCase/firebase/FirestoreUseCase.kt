package com.ezschedule.network.domain.useCase.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreUseCase {
    operator fun invoke(topic: String) = Firebase.firestore.collection(topic)
}