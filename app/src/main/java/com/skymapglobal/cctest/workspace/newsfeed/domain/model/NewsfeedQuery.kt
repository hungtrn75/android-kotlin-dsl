package com.skymapglobal.cctest.workspace.newsfeed.domain.model

data class NewsfeedQuery(
    val q: String? = null,
    val page: Int = 1,
)
