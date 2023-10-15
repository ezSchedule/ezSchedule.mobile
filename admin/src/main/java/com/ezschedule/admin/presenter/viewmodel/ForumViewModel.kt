package com.ezschedule.admin.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ezschedule.admin.domain.useCase.ForumUseCase
import com.ezschedule.network.data.ext.toObject
import com.ezschedule.network.domain.presentation.PostPresentation
import com.google.firebase.firestore.FirebaseFirestoreException

class ForumViewModel(
    private val useCase: ForumUseCase
) : ViewModel() {
    private var _posts = MutableLiveData<List<PostPresentation>>()
    val posts: LiveData<List<PostPresentation>> = _posts

    private var _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private var _error = MutableLiveData<FirebaseFirestoreException>()
    val error: LiveData<FirebaseFirestoreException> = _error

    fun getAllPosts(id: Int) {
        useCase.execute(id).addSnapshotListener { value, e ->
            when (val response = value?.toObject()) {
                null -> _error.postValue(e)

                else -> when {
                    response.isEmpty() -> _empty.postValue(Unit)

                    else -> _posts.postValue(response.map { it.toPresentation() })
                }
            }
        }
    }
}