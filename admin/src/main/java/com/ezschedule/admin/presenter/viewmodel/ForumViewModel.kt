package com.ezschedule.admin.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.admin.domain.useCase.ForumUseCase
import com.ezschedule.admin.domain.useCase.SendNotificationUseCase
import com.ezschedule.network.data.ext.toObject
import com.ezschedule.network.domain.data.NotificationRequest
import com.ezschedule.network.domain.data.PostData
import com.ezschedule.network.domain.presentation.PostPresentation
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class ForumViewModel(
    private val useCase: ForumUseCase,
    private val sendNotification: SendNotificationUseCase
) : ViewModel() {
    private var _posts = MutableLiveData<List<PostPresentation>>()
    val posts: LiveData<List<PostPresentation>> = _posts

    private var _postCreated = MutableLiveData<Unit>()
    val postCreated: LiveData<Unit> = _postCreated

    private var _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getAllPosts(id: Int) {
        useCase.execute().whereEqualTo(POST_ID, id)
            .orderBy(POST_DATE, Query.Direction.ASCENDING)
            .addSnapshotListener { value, e ->
                when (val response = value?.toObject()) {
                    null -> _error.postValue(e)

                    else -> when {
                        response.isEmpty() -> _empty.postValue(Unit)

                        else -> _posts.postValue(response.map { it.toPresentation() })
                    }
                }
            }
    }

    fun createPost(post: PostData) {
        useCase.execute().add(post)
            .addOnSuccessListener {
                _postCreated.postValue(Unit)
               send(post)
            }
            .addOnFailureListener {
                _error.postValue(it)
            }
    }

    fun send(post: PostData) = viewModelScope.launch {
        val notification = NotificationRequest(
            post.idCondominium,
            post.isEdited,
            post.textContent,
            post.typeMessage
        )
        sendNotification(notification)
    }

    fun getTypeMessage(isCommunication: Boolean) =
        if (isCommunication) POST_COMMUNICATE
        else POST_URGENT

    companion object {
        private const val POST_ID = "idCondominium"
        private const val POST_DATE = "dateTimePost"

        private const val POST_COMMUNICATE = "Communicate"
        private const val POST_URGENT = "Urgent"
    }
}