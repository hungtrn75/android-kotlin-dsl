package com.skymapglobal.cctest.workspace.newsfeed.data.remote

import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.newsfeed.data.dto.NewsRespDto
import retrofit2.http.*

interface NewsfeedService {
    @GET(Constants.topHeadLines)
    suspend fun topHeadLines(
        @Query("pageSize") pageSize: Int = Constants.pageSize,
        @Query("page") page: Int,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = Constants.newsApiKey,
        @Query("language") language: String = Constants.language,
    ): NewsRespDto

    @GET(Constants.everything)
    suspend fun everything(
        @Query("pageSize") pageSize: Int = Constants.pageSize,
        @Query("page") page: Int,
        @Query("q") q: String? = null,
        @Query("apiKey") apiKey: String = Constants.newsApiKey,
    ): NewsRespDto
}