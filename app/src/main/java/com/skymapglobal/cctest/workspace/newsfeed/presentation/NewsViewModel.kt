package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
        MutableStateFlow(NewsResp(totalResults = 0, articles = mutableListOf()))

    private val _combineSearchFlow = combine(_searching, _topHeadingsFlow) { searching, newsResp ->
        Pair(searching, newsResp)
    }
    val topHeadingsLiveData = _combineSearchFlow.asLiveData()

    fun inject(category: String) {
        this.category = category
    }

    fun refreshNews() {
        if (_topHeadingsFlow.value.totalResults == 0) {
            getNews()
        }
    }

    fun getNews(refresh: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        if (_searching.value) return@launch
        withContext(Dispatchers.Main) {
            if (refresh)
                _topHeadingsFlow.emit(NewsResp(totalResults = 0, articles = mutableListOf()))
            _searching.emit(true)
        }
        val resp = getTopHeadingsUseCase.execute(
            NewsfeedQuery(
                page = if (refresh) 1 else 1,
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
                _topHeadingsFlow.emit(newsReps)
                _searching.emit(false)
            }
        }
    }
}