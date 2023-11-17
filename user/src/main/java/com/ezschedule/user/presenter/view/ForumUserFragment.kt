package com.ezschedule.user.presenter.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezschedule.network.R.color.colorPrimary
import com.ezschedule.network.R.color.light_green_bg
import com.ezschedule.network.domain.presentation.PostPresentation
import com.ezschedule.user.presenter.adapter.PostAdapterUser
import com.ezschedule.user.presenter.viewModel.ForumUserViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.sptech.user.databinding.FragmentForumUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForumUserFragment : Fragment() {
    private lateinit var binding: FragmentForumUserBinding
    private val viewModel by viewModel<ForumUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentForumUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllPosts(SharedPreferencesManager(requireContext()).getInfo().idCondominium)
        setupLoading(true)
        setupObservers()
        setupButtonsListener()
    }

    private fun setupObservers() = with(viewModel) {
        posts.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
            setupEmptyList(false)
            setupLoading(false)
        }
        filteredPosts.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
            setupEmptyList(false)
            setupLoading(false)
        }
        empty.observe(viewLifecycleOwner) {
            setupEmptyList(true)
            binding.includeLoading.isVisible = false
        }
        error.observe(viewLifecycleOwner) {
            Log.w("ERROR", "Listen failed", it)
        }
    }

    private fun setupRecyclerView(posts: List<PostPresentation>) {
        val typeLayout = LinearLayoutManager(requireContext())
        typeLayout.stackFromEnd = true
        binding.includeForumChat.fragForumRvChat.apply {
            adapter = PostAdapterUser(posts)
            layoutManager = typeLayout
        }
    }

    private fun setupEmptyList(isEmpty: Boolean) = with(binding) {
        includeForumChat.root.isVisible = isEmpty.not()
        fragForumGroupButtons.isVisible = isEmpty.not()
        fragForumTvEmpty.isVisible = isEmpty
    }

    private fun setupButtonsListener() = with(binding) {
        fragForumGroupButtons.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                viewModel.verifyButton(checkedId)
                viewModel.setUnselectedColor(checkedId) { setBackgroundColor(it, light_green_bg) }
                setBackgroundColor(checkedId, colorPrimary)
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        includeForumChat.root.isVisible = isLoading.not()
        fragForumGroupButtons.isVisible = isLoading.not()
        includeLoading.isVisible = isLoading
    }

    private fun setBackgroundColor(id: Int, color: Int) {
        view?.findViewById<Button>(id)?.backgroundTintList = ColorStateList.valueOf(
            resources.getColor(color, requireContext().theme)
        )
    }
}