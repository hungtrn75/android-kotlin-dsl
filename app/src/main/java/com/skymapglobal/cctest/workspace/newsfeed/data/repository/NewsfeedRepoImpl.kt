package com.skymapglobal.cctest.workspace.newsfeed.data.repository

import arrow.core.Either
import com.skymapglobal.cctest.core.util.NetworkError
import com.skymapglobal.cctest.workspace.newsfeed.data.dto.mapper
import com.skymapglobal.cctest.workspace.newsfeed.data.local.NewsfeedLocalDataSource
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedRemoteDataSource
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.NewsResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo

class NewsfeedRepoImpl constructor(
    private val newsfeedRemoteDataSource: NewsfeedRemoteDataSource,
    private val newsfeedLocalDataSource: NewsfeedLocalDataSource
) :
    NewsfeedRepo {
    override suspend fun topHeadLines(page: Int, category: String): Either<String, NewsResp> = try {
        val resp = newsfeedRemoteDataSource.topHeadLines(page, category).mapper()
        Either.Right(resp)
    } catch (e: Exception) {
        Either.Left(NetworkError.handleException(e))
    }

    override suspend fun setDarkModeSetting(mode: Boolean) =
        newsfeedLocalDataSource.storeDarkModeSetting(mode)

    override suspend fun getDarkModeSetting() = newsfeedLocalDataSource.retrieveDarkModeSetting()
}