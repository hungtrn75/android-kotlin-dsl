package com.skymapglobal.cctest.workspace.newsfeed.domain.repository

import arrow.core.Either
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp

interface NewsfeedRepo {
    suspend fun topHeadLines(page: Int, category: String): Either<String, NewsResp>
    suspend fun setDarkModeSetting(mode: Boolean)
    suspend fun getDarkModeSetting(): Boolean
}