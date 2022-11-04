package com.skymapglobal.cctest.workspace.newsfeed.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skymapglobal.cctest.databinding.FragmentNewsBinding
import com.skymapglobal.cctest.workspace.details.presentation.DetailsActivity
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter.NewsViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CATEGORY = "category"

class NewsFragment : Fragment(), NewsViewAdapter.OnNewsListener {
    private var category: String? = null
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newViewAdapter: NewsViewAdapter
    private val viewModel: NewsViewModel by viewModel()

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
            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.searchSize - 1) {
                viewModel.getNews()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY, category)
                }
            }
    }

    override fun onNewsClick(item: Article) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.article, item)
        startActivity(intent)
    }
}