package com.skymapglobal.cctest.workspace.newsfeed.data.remote

import com.skymapglobal.cctest.workspace.newsfeed.data.dto.NewsRespDto

interface NewsfeedRemoteDataSource {
    suspend fun topHeadLines(page: Int, category: String): NewsRespDto
}

