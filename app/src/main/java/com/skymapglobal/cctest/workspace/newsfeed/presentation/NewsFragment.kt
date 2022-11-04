package com.skymapglobal.cctest.workspace.newsfeed.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skymapglobal.cctest.databinding.FragmentNewsBinding
import com.skymapglobal.cctest.workspace.details.presentation.DetailsActivity
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter.NewsViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment() : Fragment(),
    NewsViewAdapter.OnNewsListener {
    private var category: String? = null
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newViewAdapter: NewsViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            category = it.getString(CATEGORY)
            viewModel.inject(category!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsBinding.inflate(inflater, container, false)

        settingViews()
        settingListeners()
        viewModel.refreshNews()
        return binding.root
    }

    private fun settingViews() {
        newViewAdapter = NewsViewAdapter(this)
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newViewAdapter
            setHasFixedSize(false)
        }
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getNews(refresh = true)
        }
    }

    private fun settingListeners() {
        viewModel.topHeadingsLiveData.observe(viewLifecycleOwner) {
            binding.swipeContainer.isRefreshing = false
            newViewAdapter.submitList(it.articles)
        }
        binding.recycleView.addOnScrollListener(onScrollChangeListener)
    }

    private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = binding.recycleView.layoutManager as LinearLayoutManager?

            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() > 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.searchSize - 1) {
                viewModel.getNews()
            }
        }
    }

    override fun onNewsClick(item: Article) {
        if (item.url!!.contains("youtube")) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
        } else {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.article, item)
            startActivity(intent)
        }
    }

    override fun onRetryClick() {
        viewModel.retry()
    }

    override fun onShare(item: Article) {
        ShareCompat.IntentBuilder(requireContext())
            .setType("text/plain")
            .setChooserTitle(item.title)
            .setText(item.url)
            .startChooser()
    }

    companion object {
        const val CATEGORY = "category"

        @JvmStatic
        fun newInstance(category: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY, category)
                }
            }
    }
}