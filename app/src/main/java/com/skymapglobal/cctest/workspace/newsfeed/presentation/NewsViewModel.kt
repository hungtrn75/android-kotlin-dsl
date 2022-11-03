package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsViewModel constructor(private val getTopHeadingsUseCase: GetTopHeadingsUseCase) :
    ViewModel() {
    lateinit var category: String

    private val _searching = MutableStateFlow(false)
    private val _topHeadingsFlow =
        MutableStateFlow(NewsResp(totalResults = 0, articles = mutableListOf()))
    val topHeadingsLiveData = _topHeadingsFlow.asLiveData()

    fun inject(category: String) {
        this.category = category
    }

    fun refreshNews() {
        if (_topHeadingsFlow.value.totalResults == 0) {
            getNews()
        }
    }

    fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        val resp = getTopHeadingsUseCase.execute(NewsfeedQuery(page = 1, category = category))
        resp.fold({ error ->
            Timber.e("getNews: $error")
        }) { newsReps ->
            withContext(Dispatchers.Main) {
                _topHeadingsFlow.emit(newsReps)
            }

            Timber.e("getNews: ${newsReps.articles}")
        }
    }
}