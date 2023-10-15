package com.ezschedule.admin.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.admin.databinding.AdapterPostBinding
import com.ezschedule.network.domain.presentation.PostPresentation

class PostAdapter(
    private val posts: List<PostPresentation>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = posts.size

    inner class ViewHolder(private val binding: AdapterPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            fragForumTvDate.text = postData().date
            fragForumTvContent.text = postData().content
        }

        private fun postData() = posts[adapterPosition]
    }
}