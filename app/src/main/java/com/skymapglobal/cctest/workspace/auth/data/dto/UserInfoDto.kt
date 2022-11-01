package com.skymapglobal.cctest.workspace.auth.data.dto

import com.skymapglobal.cctest.workspace.auth.domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val email: String,
    val name: String,
    val role: String,
    val uuid: String?,
    val avatar: String?,
)

fun UserInfoDto.mapper() =
    UserInfo(name = name, email = email, role = role, uuid = uuid, avatar = avatar)
