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
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val placeholder: ArticlePlaceholder? = ArticlePlaceholder.Article,
) : Parcelable

@Parcelize
data class Source(
    val name: String?
) : Parcelable

@Parcelize
sealed class ArticlePlaceholder : Parcelable {
    object Article : ArticlePlaceholder()
    object FistPageLoading : ArticlePlaceholder()
    object Loading : ArticlePlaceholder()
    class Error(val error: String) : ArticlePlaceholder()
}