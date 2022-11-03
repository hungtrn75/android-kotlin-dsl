package com.skymapglobal.cctest.workspace.newsfeed.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.skymapglobal.cctest.databinding.FragmentNewsBinding
import com.skymapglobal.cctest.workspace.details.presentation.DetailsActivity
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter.NewsViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

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
            Timber.e("onCreate: $category")
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
    }

    private fun settingListeners() {
        viewModel.topHeadingsLiveData.observe(viewLifecycleOwner) {
            newViewAdapter.submitList(it.articles)
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