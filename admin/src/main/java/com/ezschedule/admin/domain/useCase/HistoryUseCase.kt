package com.ezschedule.admin.domain.useCase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HistoryUseCase {
    operator fun invoke(topic: String) = Firebase.firestore.collection(topic)
}