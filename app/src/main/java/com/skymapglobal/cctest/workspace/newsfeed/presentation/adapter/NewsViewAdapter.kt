package com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.skymapglobal.cctest.R
import com.skymapglobal.cctest.databinding.ItemNewsBinding
import com.skymapglobal.cctest.databinding.ItemNewsFirstBinding
import com.skymapglobal.cctest.databinding.ItemNewsFirstShimmerBinding
import com.skymapglobal.cctest.databinding.ItemNewsShimmerBinding
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class NewsViewAdapter(private val listener: OnNewsListener) :
    ListAdapter<Article, NewsViewAdapter.BaseViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewAdapter.BaseViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val firstBinding =
            ItemNewsFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        if (getItem(viewType).isPlaceHolder == true) {
            val shimmerBinding =
                ItemNewsShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            if (viewType == 0) {
                val shimmerFirstBinding =
                    ItemNewsFirstShimmerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                return NewsShimmerFirstViewHolder(shimmerFirstBinding/**/)
            }
            return NewsShimmerViewHolder(shimmerBinding)
        }
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
        fun getShimmerDrawable(): ShimmerDrawable {
            val shimmer = Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            return ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
        }

        fun parseDateTime(dateTime: String): String {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            format.timeZone = TimeZone.getTimeZone("UTC")
            val prettyTime = PrettyTime(Locale.ENGLISH)
            return prettyTime.format(format.parse(dateTime))
        }
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
            if (item.urlToImage != null) {
                Glide.with(context)
                    .load(item.urlToImage)
                    .placeholder(getShimmerDrawable())
                    .centerCrop()
                    .into(binding.thumbnailBottom)
            } else {
                binding.thumbnailBottom.visibility = View.GONE
            }
            binding.title.apply {
                visibility = if (item.title == null) View.GONE else View.VISIBLE
                text = item.title
            }
            binding.description.apply {
                visibility = if (item.description == null) View.GONE else View.VISIBLE
                text = item.description
            }
            val info = "${parseDateTime(item.publishedAt!!)} | ${item.source?.name}"
            binding.info.text = info
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
            if (item.urlToImage != null) {
                Glide.with(context).load(item.urlToImage)
                    .placeholder(getShimmerDrawable())
                    .error(ColorDrawable(ContextCompat.getColor(context, R.color.colorShimmer)))
                    .centerCrop()
                    .into(binding.thumbnailTop)
            } else {
                binding.thumbnailTop.visibility = View.GONE
            }
            binding.title.apply {
                visibility = if (item.title == null) View.GONE else View.VISIBLE
                text = item.title
            }
            binding.description.apply {
                visibility = if (item.description == null) View.GONE else View.VISIBLE
                text = item.description
            }
            val info = "${parseDateTime(item.publishedAt!!)} | ${item.source?.name}"
            binding.info.text = info
        }
    }

    inner class NewsShimmerViewHolder(
        binding: ItemNewsShimmerBinding
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: Article, position: Int) {

        }

    }

    inner class NewsShimmerFirstViewHolder(
        binding: ItemNewsFirstShimmerBinding
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: Article, position: Int) {

        }

    }

    interface OnNewsListener {
        fun onNewsClick(item: Article)
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}