package com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.skymapglobal.cctest.databinding.ItemNewsBinding
import com.skymapglobal.cctest.databinding.ItemNewsFirstBinding
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article

class NewsViewAdapter(private val listener: OnNewsListener) :
    ListAdapter<Article, NewsViewAdapter.BaseViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewAdapter.BaseViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val firstBinding =
            ItemNewsFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            0 -> NewsFirstViewHolder(firstBinding, parent.context)
            else -> NewsViewHolder(binding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: NewsViewAdapter.BaseViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    abstract inner class BaseViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: Article, position: Int)
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding, private val context: Context) :
        BaseViewHolder(binding) {
        init {
            binding.root.setOnClickListener {
                listener.onNewsClick(getItem(bindingAdapterPosition))
            }
            binding.shareBtn.setOnClickListener {
            }
        }

        override fun bind(item: Article, position: Int) {
            Glide.with(context).load(item.urlToImage)
                .centerCrop()
                .into(binding.thumbnailBottom)

            binding.title.apply {
                visibility = if (item.title == null) View.GONE else View.VISIBLE
                text = item.title
            }
            binding.description.apply {
                visibility = if (item.description == null) View.GONE else View.VISIBLE
                text = item.description
            }

        }
    }

    inner class NewsFirstViewHolder(
        private val binding: ItemNewsFirstBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding) {
        init {
            binding.root.setOnClickListener {
                listener.onNewsClick(getItem(bindingAdapterPosition))
            }
            binding.shareBtn.setOnClickListener {
            }
        }

        override fun bind(item: Article, position: Int) {
            Glide.with(context).load(item.urlToImage)
                .centerCrop()
                .into(binding.thumbnailTop)

            binding.title.apply {
                visibility = if (item.title == null) View.GONE else View.VISIBLE
                text = item.title
            }
            binding.description.apply {
                visibility = if (item.description == null) View.GONE else View.VISIBLE
                text = item.description
            }

        }
    }

    interface OnNewsListener {
        fun onNewsClick(item: Article)
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }
}