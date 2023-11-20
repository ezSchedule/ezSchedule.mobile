package com.ezschedule.admin.presenter.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezschedule.admin.databinding.FragmentForumBinding
import com.ezschedule.admin.presenter.adapter.PostAdapter
import com.ezschedule.admin.presenter.viewmodel.ForumViewModel
import com.ezschedule.network.domain.data.PostData
import com.ezschedule.network.domain.presentation.PostPresentation
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.utils.SharedPreferencesManager
import com.google.firebase.Timestamp
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding
    private lateinit var typeMessage: String
    private lateinit var sharedPreferences: TenantPresentation
    private val viewModel by viewModel<ForumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = SharedPreferencesManager(requireContext()).getInfo()
        typeMessage = viewModel.getTypeMessage(isCommunication = true)
        viewModel.getAllPosts(sharedPreferences.idCondominium)
        setupLoading(true)
        setupObservers()
        setupClick()
    }

    private fun setupObservers() = with(viewModel) {
        posts.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
            setupEmptyList(false)
            setupLoading(false)
        }
        postCreated.observe(viewLifecycleOwner) {
            changeScreen(isCreating = false)
            view?.let { view -> requireContext().hideKeyboard(view) }
        }
        empty.observe(viewLifecycleOwner) {
            setupEmptyList(true)
            binding.includeLoading.isVisible = false
        }
        error.observe(viewLifecycleOwner) {
            Log.w("ERROR", "Listen failed", it)
        }
    }

    private fun setupEmptyList(isEmpty: Boolean) = with(binding) {
        includeForumChat.root.isVisible = isEmpty.not()
        fragForumTvEmpty.isVisible = isEmpty
    }

    private fun setupRecyclerView(posts: List<PostPresentation>) {
        val typeLayout = LinearLayoutManager(requireContext())
        typeLayout.stackFromEnd = true
        binding.includeForumChat.fragForumRvChat.apply {
            adapter = PostAdapter(posts)
            layoutManager = typeLayout
        }
    }

    private fun setupClick() = with(binding) {
        fragForumBtnAdd.setOnClickListener {
            changeScreen(isCreating = true)
        }
        includeForumCreatePost.apply {
            fragForumIbPrevious.setOnClickListener {
                changeScreen(isCreating = false)
                view?.let { view -> requireContext().hideKeyboard(view) }
            }
            fragForumIbSend.setOnClickListener {
                viewModel.createPost(
                    PostData(
                        dateTimePost = Timestamp(Date()),
                        idCondominium = sharedPreferences.idCondominium.toLong(),
                        isEdited = false,
                        textContent = fragForumEtText.text.toString(),
                        typeMessage = typeMessage
                    )
                )
            }
            fragForumBtnCommunication.setOnClickListener {
                changeTypePost(isCommunication = true)
            }
            fragForumBtnUrgent.setOnClickListener {
                changeTypePost(isCommunication = false)
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        includeForumChat.root.isVisible = isLoading.not()
        fragForumBtnAdd.isVisible = isLoading.not()
        includeLoading.isVisible = isLoading
    }

    private fun changeScreen(isCreating: Boolean) = with(binding) {
        includeForumChat.root.isVisible = isCreating.not()
        fragForumBtnAdd.isVisible = isCreating.not()
        includeForumCreatePost.root.isVisible = isCreating
    }

    private fun changeTypePost(isCommunication: Boolean) = with(binding.includeForumCreatePost) {
        typeMessage = viewModel.getTypeMessage(isCommunication)
        fragForumBtnCommunication.isEnabled = isCommunication.not()
        fragForumBtnUrgent.isEnabled = isCommunication
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}