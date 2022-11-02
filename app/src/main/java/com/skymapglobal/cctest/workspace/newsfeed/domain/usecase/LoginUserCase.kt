package com.skymapglobal.cctest.workspace.newsfeed.domain.usecase

import arrow.core.Either
import com.skymapglobal.cctest.core.NoParamsUseCase
import com.skymapglobal.cctest.core.UseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.UserInfo
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo

class LoginUseCase constructor(private val newsfeedRepo: NewsfeedRepo) :
    UseCase<Either<String, LoginResp>, Map<String, String>>() {
    override suspend fun execute(params: Map<String, String>) =
        newsfeedRepo.login(params)
}

class MeUseCase constructor(private val newsfeedRepo: NewsfeedRepo) :
    NoParamsUseCase<Either<String, UserInfo>>() {
    override suspend fun execute() = newsfeedRepo.me()
}