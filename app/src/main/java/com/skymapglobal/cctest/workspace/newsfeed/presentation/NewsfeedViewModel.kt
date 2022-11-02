package com.skymapglobal.cctest.workspace.newsfeed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsfeedQuery
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetNewsUseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsfeedViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val getTopHeadingsUseCase: GetTopHeadingsUseCase
) : ViewModel() {
    fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        val resp = getNewsUseCase.execute(NewsfeedQuery(page = 1, q = "bitcoin"))
        resp.fold({ error ->
            Timber.e("getNews: $error")
        }) { newsReps ->
            Timber.e("getNews: ${newsReps.articles}")
        }
    }
}
