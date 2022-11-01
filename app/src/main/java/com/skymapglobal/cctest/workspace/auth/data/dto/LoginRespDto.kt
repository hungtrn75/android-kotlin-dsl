package com.skymapglobal.cctest.workspace.auth.data.dto

import com.skymapglobal.cctest.workspace.auth.domain.model.LoginResp
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

@Serializable
data class LoginRespDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
)

fun LoginRespDto.mapper() = LoginResp(accessToken = accessToken)

