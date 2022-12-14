package com.skymapglobal.cctest.workspace.newsfeed.data.remote

class NewsfeedRemoteDataSourceImpl constructor(private val newsfeedService: NewsfeedService) :
    NewsfeedRemoteDataSource {
    override suspend fun topHeadLines(page: Int, category: String) =
        newsfeedService.topHeadLines(page = page, category = category)
}