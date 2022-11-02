package com.skymapglobal.cctest.workspace.newsfeed.data.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Article
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.Source

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategies.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

data class NewsRespDto(
    val status: String? = null,
    val totalResults: Long? = null,
    val articles: List<ArticleDto>? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<NewsResp>(json)
    }
}

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


fun SourceDto.mapper() = Source(id = id, name = name!!)

fun ArticleDto.mapper() = Article(
    source = source?.mapper(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt!!,
    content = content!!,
)

fun NewsRespDto.mapper() =
    NewsResp(totalResults = totalResults!!, articles = articles!!.map { it.mapper() }.toList())