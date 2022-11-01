package com.skymapglobal.cctest.workspace.auth.data.remote

import com.skymapglobal.cctest.workspace.auth.data.dto.LoginRespDto
import com.skymapglobal.cctest.workspace.auth.data.dto.UserInfoDto

interface AuthRemoteDataSource {
    abstract suspend fun login(body: Map<String, String>): LoginRespDto
    abstract suspend fun me(): UserInfoDto
}

