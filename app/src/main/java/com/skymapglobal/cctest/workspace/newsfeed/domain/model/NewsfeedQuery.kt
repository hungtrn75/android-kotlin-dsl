package com.skymapglobal.cctest.workspace.newsfeed.domain.model

data class NewsfeedQuery(
    val category: String,
    val page: Int = 1,
)
