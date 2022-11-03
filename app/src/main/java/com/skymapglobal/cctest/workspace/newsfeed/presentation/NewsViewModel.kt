package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsViewModel constructor(private val getTopHeadingsUseCase: GetTopHeadingsUseCase) :
    ViewModel() {
    lateinit var category: String

    private val _searching = MutableStateFlow(false)
    private val _topHeadingsFlow =
        MutableStateFlow(NewsResp(totalResults = Int.MAX_VALUE, articles = mutableListOf()))

    private val _combineSearchFlow = combine(_searching, _topHeadingsFlow) { searching, newsResp ->
        Pair(searching, newsResp)
    }
    val topHeadingsLiveData = _combineSearchFlow.asLiveData()
    val searchSize: Int get() = _topHeadingsFlow.value.articles?.size ?: 0

    fun inject(category: String) {
        this.category = category
    }

    fun refreshNews() {
        if (_topHeadingsFlow.value.totalResults == 0 || _topHeadingsFlow.value.totalResults == Int.MAX_VALUE) {
            getNews(refresh = true)
        }
    }

    fun getNews(refresh: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        if (_searching.value || searchSize >= _topHeadingsFlow.value.totalResults) return@launch
        withContext(Dispatchers.Main) {
            if (refresh)
                _topHeadingsFlow.emit(
                    NewsResp(
                        totalResults = Int.MAX_VALUE,
                        articles = mutableListOf()
                    )
                )
            _searching.emit(true)
        }
        val previousArticles = _topHeadingsFlow.value.articles?.toMutableList() ?: mutableListOf()
        val resp = getTopHeadingsUseCase.execute(
            NewsfeedQuery(
                page = if (refresh) 1 else (previousArticles.size / Constants.pageSize) + 1,
                category = category
            )
        )
        resp.fold({ error ->
            Timber.e("getNews: $error")
            withContext(Dispatchers.Main) {
                _searching.emit(false)
            }
        }) { newsReps ->
            withContext(Dispatchers.Main) {
                previousArticles.addAll(newsReps.articles ?: mutableListOf())
                _topHeadingsFlow.emit(newsReps.copy(articles = previousArticles))
                _searching.emit(false)
            }
        }
    }
}