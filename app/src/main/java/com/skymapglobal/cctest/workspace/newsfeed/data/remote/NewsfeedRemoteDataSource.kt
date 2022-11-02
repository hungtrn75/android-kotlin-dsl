package com.skymapglobal.cctest.workspace.newsfeed.data.remote

import com.skymapglobal.cctest.workspace.newsfeed.data.dto.LoginRespDto
import com.skymapglobal.cctest.workspace.newsfeed.data.dto.UserInfoDto

interface NewsfeedRemoteDataSource {
    abstract suspend fun login(body: Map<String, String>): LoginRespDto
    abstract suspend fun me(): UserInfoDto
}

