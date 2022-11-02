package com.skymapglobal.cctest.workspace.newsfeed.data.remote

class NewsfeedRemoteDataSourceImpl constructor(private val newsfeedService: NewsfeedService) :
    NewsfeedRemoteDataSource {
    override suspend fun topHeadLines(page: Int, q: String?) = newsfeedService.topHeadLines(page = page, q = q)

    override suspend fun everything(page: Int, q: String?) = newsfeedService.everything(page = page, q = q)
}