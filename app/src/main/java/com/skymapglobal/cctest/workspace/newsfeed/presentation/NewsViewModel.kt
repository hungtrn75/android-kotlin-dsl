package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.ArticlePlaceholder
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
* use coroutineScope instead of viewModelScope to memo tabs
* */
class NewsViewModel constructor(
    private val getTopHeadingsUseCase: GetTopHeadingsUseCase,
    private val coroutineScope: CoroutineScope
) :
    ViewModel() {
    lateinit var category: String
    private var _searching = false

    private val _topHeadingsFlow =
        MutableStateFlow(NewsResp(totalResults = Int.MAX_VALUE, articles = mutableListOf()))
    val topHeadingsLiveData = _topHeadingsFlow.asLiveData()

    val searchSize: Int get() = _topHeadingsFlow.value.articles?.size ?: 0

    fun inject(category: String) {
        this.category = category
    }

    fun refreshNews() {
        if (_topHeadingsFlow.value.totalResults == 0 || _topHeadingsFlow.value.totalResults == Int.MAX_VALUE) {
            getNews(refresh = true)
        }
    }

    fun retry() = coroutineScope.launch {
        if (searchSize == 1) {
            getNews(refresh = true)
        } else {
            val previousArticles =
                _topHeadingsFlow.value.articles?.toMutableList() ?: mutableListOf()
            previousArticles.removeLast()
            _topHeadingsFlow.emit(_topHeadingsFlow.value.copy(articles = previousArticles.toMutableList()))
            getNews()
        }
    }

    /* pull to refresh -> refresh: true */
    fun getNews(refresh: Boolean = false) {
        coroutineScope.launch(Dispatchers.IO) {
            if (_searching || searchSize >= _topHeadingsFlow.value.totalResults) return@launch
            _searching = true
            val previousArticles = if (refresh) mutableListOf() else
                _topHeadingsFlow.value.articles?.toMutableList() ?: mutableListOf()

            withContext(Dispatchers.Main) {
                if (refresh) {
                    previousArticles.addAll(
                        mutableListOf(
                            Article(placeholder = ArticlePlaceholder.FistPageLoading),
                            Article(placeholder = ArticlePlaceholder.Loading),
                        )
                    )
                    _topHeadingsFlow.emit(
                        NewsResp(
                            totalResults = Int.MAX_VALUE,
                            articles = previousArticles.toMutableList()
                        )
                    )
                } else {
                    previousArticles.add(Article(placeholder = ArticlePlaceholder.Loading))
                    _topHeadingsFlow.emit(_topHeadingsFlow.value.copy(articles = previousArticles.toMutableList()))
                }
            }


            val resp = getTopHeadingsUseCase.execute(
                NewsfeedQuery(
                    page = if (refresh) 1 else (previousArticles.size / Constants.pageSize) + 1,
                    category = category
                )
            )
            previousArticles.removeLast()
            if (refresh) previousArticles.removeLast()
            resp.fold({ error ->
                previousArticles.add(Article(placeholder = ArticlePlaceholder.Error(error)))
                withContext(Dispatchers.Main) {
                    _topHeadingsFlow.emit(_topHeadingsFlow.value.copy(articles = previousArticles.toMutableList()))
                    _searching = false
                }
            }) { newsReps ->
                previousArticles.addAll(newsReps.articles ?: mutableListOf())
                withContext(Dispatchers.Main) {
                    _topHeadingsFlow.emit(newsReps.copy(articles = previousArticles.toMutableList()))
                    _searching = false
                }
            }
        }
    }
}