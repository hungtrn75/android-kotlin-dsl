package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsViewModel constructor(private val getTopHeadingsUseCase: GetTopHeadingsUseCase) :
    ViewModel() {
    lateinit var category: String

    fun inject(category: String) {
        this.category = category
    }

    fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        val resp = getTopHeadingsUseCase.execute(NewsfeedQuery(page = 1, category = category))
        resp.fold({ error ->
            Timber.e("getNews: $error")
        }) { newsReps ->
            Timber.e("getNews: ${newsReps.articles}")
        }
    }
}