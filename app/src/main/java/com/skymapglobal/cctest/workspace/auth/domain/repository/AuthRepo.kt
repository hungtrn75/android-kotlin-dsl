package com.skymapglobal.cctest.workspace.auth.domain.repository

import arrow.core.Either
import com.skymapglobal.cctest.workspace.auth.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.auth.domain.model.UserInfo

interface AuthRepo {
    abstract suspend fun login(body: Map<String, String>): Either<String, LoginResp>
    abstract suspend fun me(): Either<String, UserInfo>
}