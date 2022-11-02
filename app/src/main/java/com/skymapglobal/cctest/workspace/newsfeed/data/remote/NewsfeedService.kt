package com.skymapglobal.cctest.workspace.newsfeed.data.remote

import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.newsfeed.data.dto.LoginRespDto
import com.skymapglobal.cctest.workspace.newsfeed.data.dto.UserInfoDto
import retrofit2.http.*

interface NewsfeedService {
    @Headers("Content-Type: application/json")
    @POST(Constants.login)
    suspend fun login(
        @Body json: Map<String, String>
    ): LoginRespDto

    @GET(Constants.me)
    suspend fun me(): UserInfoDto
}