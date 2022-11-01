package com.skymapglobal.cctest.workspace.auth.data.repository

import arrow.core.Either
import com.skymapglobal.cctest.core.util.NetworkError
import com.skymapglobal.cctest.workspace.auth.data.dto.mapper
import com.skymapglobal.cctest.workspace.auth.data.remote.AuthRemoteDataSource
import com.skymapglobal.cctest.workspace.auth.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.auth.domain.model.UserInfo
import com.skymapglobal.cctest.workspace.auth.domain.repository.AuthRepo

class AuthRepoImpl constructor(private val authRemoteDataSource: AuthRemoteDataSource) : AuthRepo {
    override suspend fun login(body: Map<String, String>): Either<String, LoginResp> = try {
        val resp = authRemoteDataSource.login(body).mapper()
        Either.Right(resp)
    } catch (e: Exception) {
        Either.Left(NetworkError.handleException(e))
    }

    override suspend fun me(): Either<String, UserInfo> = try {
        val resp = authRemoteDataSource.me().mapper()
        Either.Right(resp)
    } catch (e: Exception) {
        Either.Left(NetworkError.handleException(e))
    }
}