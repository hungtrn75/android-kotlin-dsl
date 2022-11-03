package com.skymapglobal.cctest.workspace.newsfeed.domain.repository

import arrow.core.Either
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp

interface NewsfeedRepo {
    abstract suspend fun topHeadLines(page: Int, category: String): Either<String, NewsResp>
    abstract suspend fun everything(page: Int, category: String): Either<String, NewsResp>
    abstract suspend fun setDarkModeSetting(mode: Boolean)
    abstract suspend fun getDarkModeSetting(): Boolean
}