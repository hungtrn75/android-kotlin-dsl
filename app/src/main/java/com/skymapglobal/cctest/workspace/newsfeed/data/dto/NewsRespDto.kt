package com.skymapglobal.cctest.workspace.newsfeed.data.dto

import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Source


data class NewsRespDto(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleDto>? = null
)

data class ArticleDto(
    val source: SourceDto? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)

data class SourceDto(
    val id: String? = null,
    val name: String? = null
)


fun SourceDto.mapper() = Source(name = name)

fun ArticleDto.mapper() = Article(
    source = source?.mapper(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
)

fun NewsRespDto.mapper() =
    NewsResp(totalResults = totalResults!!, articles = articles!!.map { it.mapper() }.toList())