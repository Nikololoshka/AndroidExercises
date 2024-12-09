package com.vereshchagin.nikolay.rxjava.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vereshchagin.nikolay.rxjava.api.Post
import com.vereshchagin.nikolay.rxjava.databinding.ItemPostBinding
import io.reactivex.rxjava3.core.Observer

class PostListAdapter(
    private val itemClicked: Observer<Int>
) : ListAdapter<Post, PostViewHolder>(PostDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding, itemClicked)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val itemClicked: Observer<Int>
): RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener { itemClicked.onNext(bindingAdapterPosition) }
    }

    fun bind(post: Post) {
        binding.title.text = post.title
        binding.body.text =  post.body
    }
}

private object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}