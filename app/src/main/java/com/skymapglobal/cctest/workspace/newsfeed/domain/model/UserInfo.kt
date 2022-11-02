package com.skymapglobal.cctest.workspace.newsfeed.domain.model

data class UserInfo(
    val email: String,
    val name: String,
    val role: String,
    val uuid: String?,
    val avatar: String?,
)