package com.ezschedule.user.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ezschedule.network.data.ext.toObject
import com.ezschedule.network.domain.presentation.PostPresentation
import com.ezschedule.network.domain.useCase.firebase.FirestoreUseCase
import com.google.firebase.firestore.Query
import com.sptech.user.R

class ForumUserViewModel(private val useCase: FirestoreUseCase) : ViewModel() {
    private var _posts = MutableLiveData<List<PostPresentation>>()
    val posts: LiveData<List<PostPresentation>> = _posts

    private var _filteredPosts = MutableLiveData<List<PostPresentation>>()
    val filteredPosts: LiveData<List<PostPresentation>> = _filteredPosts

    private var _empty = MutableLiveData<Unit>()
    val empty: LiveData<Unit> = _empty

    private var _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getAllPosts(id: Int) {
        useCase("conversations-$id")
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

    fun verifyButton(id: Int) {
        when (id) {
            R.id.frag_forum_btn_communicate -> getFilteredPosts(POST_COMMUNICATE)
            R.id.frag_forum_btn_all -> _posts.postValue(posts.value)
            R.id.frag_forum_btn_urgent -> getFilteredPosts(POST_URGENT)
        }
    }

    fun setUnselectedColor(id: Int, behavior: (id: Int) -> Unit) {
        arrayListOf(
            R.id.frag_forum_btn_communicate, R.id.frag_forum_btn_all, R.id.frag_forum_btn_urgent
        ).map {
            if (it != id) behavior(it)
        }
    }

    private fun getFilteredPosts(type: String) {
        val filteredList = posts.value!!.mapNotNull {
            it.takeIf { it.type == type }
        }

        when {
            filteredList.isEmpty() -> _empty.postValue(Unit)

            else -> _filteredPosts.postValue(filteredList)
        }
    }

    companion object {
        private const val POST_DATE = "dateTimePost"

        private const val POST_COMMUNICATE = "Communicate"
        private const val POST_URGENT = "Urgent"
    }
}