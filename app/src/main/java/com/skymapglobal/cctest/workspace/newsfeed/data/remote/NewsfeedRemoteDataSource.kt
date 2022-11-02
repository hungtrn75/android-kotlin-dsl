package com.skymapglobal.cctest.workspace.newsfeed.data.remote

import com.skymapglobal.cctest.workspace.newsfeed.data.dto.NewsRespDto

interface NewsfeedRemoteDataSource {
    abstract suspend fun topHeadLines(page: Int, q: String?): NewsRespDto
    abstract suspend fun everything(page: Int, q: String?): NewsRespDto
}

