package com.ezschedule.admin.presenter.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezschedule.admin.databinding.FragmentForumBinding
import com.ezschedule.admin.presenter.adapter.PostAdapter
import com.ezschedule.admin.presenter.viewmodel.ForumViewModel
import com.ezschedule.network.domain.presentation.PostPresentation
import com.ezschedule.utils.SharedPreferencesManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForumFragment : Fragment() {
    private lateinit var binding: FragmentForumBinding
    private val viewModel by viewModel<ForumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllPosts(
            SharedPreferencesManager(requireContext()).getInfo().idCondominium
        )
        setupLoading(true)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        posts.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
            setupEmptyList(false)
            setupLoading(false)
        }
        empty.observe(viewLifecycleOwner) {
            setupEmptyList(true)
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

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        includeForumChat.root.isVisible = isLoading.not()
        fragForumBtnAdd.isVisible = isLoading.not()
        includeLoading.apply {
            isVisible = isLoading
            setBackgroundColor(Color.TRANSPARENT)
        }
    }
}