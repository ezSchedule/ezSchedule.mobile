package com.ezschedule.user.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezschedule.network.domain.presentation.PostPresentation
import com.sptech.user.databinding.AdapterPostUserBinding

class PostAdapterUser(
    private val posts: List<PostPresentation>
) : RecyclerView.Adapter<PostAdapterUser.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterPostUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = posts.size

    inner class ViewHolder(private val binding: AdapterPostUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() = with(binding) {
            fragForumTvDate.text = postData().date
            fragForumTvContent.text = postData().content
        }

        private fun postData() = posts[adapterPosition]
    }
}