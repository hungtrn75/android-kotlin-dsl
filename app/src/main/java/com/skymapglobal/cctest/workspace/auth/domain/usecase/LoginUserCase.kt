package com.skymapglobal.cctest.workspace.auth.domain.usecase

import arrow.core.Either
import com.skymapglobal.cctest.core.NoParamsUseCase
import com.skymapglobal.cctest.core.UseCase
import com.skymapglobal.cctest.workspace.auth.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.auth.domain.model.UserInfo
import com.skymapglobal.cctest.workspace.auth.domain.repository.AuthRepo

class LoginUseCase constructor(private val authRepo: AuthRepo) :
    UseCase<Either<String, LoginResp>, Map<String, String>>() {
    override suspend fun execute(params: Map<String, String>) =
        authRepo.login(params)
}

class MeUseCase constructor(private val authRepo: AuthRepo) :
    NoParamsUseCase<Either<String, UserInfo>>() {
    override suspend fun execute() = authRepo.me()
}