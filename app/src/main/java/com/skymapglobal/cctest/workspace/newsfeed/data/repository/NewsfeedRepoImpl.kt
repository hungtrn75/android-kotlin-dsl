package com.skymapglobal.cctest.workspace.newsfeed.data.repository

import arrow.core.Either
import com.skymapglobal.cctest.core.util.NetworkError
import com.skymapglobal.cctest.workspace.newsfeed.data.dto.mapper
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedRemoteDataSource
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.UserInfo
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo

class NewsfeedRepoImpl constructor(private val newsfeedRemoteDataSource: NewsfeedRemoteDataSource) : NewsfeedRepo {
    override suspend fun login(body: Map<String, String>): Either<String, LoginResp> = try {
        val resp = newsfeedRemoteDataSource.login(body).mapper()
        Either.Right(resp)
    } catch (e: Exception) {
        Either.Left(NetworkError.handleException(e))
    }

    override suspend fun me(): Either<String, UserInfo> = try {
        val resp = newsfeedRemoteDataSource.me().mapper()
        Either.Right(resp)
    } catch (e: Exception) {
        Either.Left(NetworkError.handleException(e))
    }
}