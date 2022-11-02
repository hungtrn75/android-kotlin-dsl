package com.skymapglobal.cctest.workspace.newsfeed.domain.repository

import arrow.core.Either
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.LoginResp
import com.skymapglobal.cctest.workspace.newsfeed.domain.model.UserInfo

interface NewsfeedRepo {
    abstract suspend fun login(body: Map<String, String>): Either<String, LoginResp>
    abstract suspend fun me(): Either<String, UserInfo>
}