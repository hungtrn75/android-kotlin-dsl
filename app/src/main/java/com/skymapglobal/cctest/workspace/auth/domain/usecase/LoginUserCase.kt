package com.skymapglobal.cctest.workspace.auth.domain.usecase

import arrow.core.Either
import com.skymapglobal.cctest.core.NoParamsUseCase
import com.skymapglobal.cctest.core.UseCase
import com.skymapglobal.cctest.workspace.auth.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.auth.domain.model.UserInfo
import com.skymapglobal.cctest.workspace.auth.domain.repository.AuthRepo

class LoginUseCase constructor(private val authRepo: AuthRepo) :
    UseCase<Either<String, LoginResp>, Map<String, String>>() {
    override suspend fun execute(body: Map<String, String>): Either<String, LoginResp> =
        authRepo.login(body)
}

class MeUseCase constructor(private val authRepo: AuthRepo) :
    NoParamsUseCase<Either<String, UserInfo>>() {
    override suspend fun execute(): Either<String, UserInfo> = authRepo.me()
}