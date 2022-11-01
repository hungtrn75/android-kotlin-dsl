package com.skymapglobal.cctest.workspace.auth.domain.model

data class UserInfo(
    val email: String,
    val name: String,
    val role: String,
    val uuid: String?,
    val avatar: String?,
)