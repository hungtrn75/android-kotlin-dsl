package com.skymapglobal.cctest.workspace.newsfeed.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResp(
    val totalResults: Int,
    val articles: List<Article>?
) : Parcelable

@Parcelize
data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable