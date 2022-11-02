package com.skymapglobal.cctest.workspace.newsfeed.data.remote

class NewsfeedRemoteDataSourceImpl constructor(private val newsfeedService: NewsfeedService) :
    NewsfeedRemoteDataSource {
    override suspend fun login(body: Map<String, String>) = newsfeedService.login(body)

    override suspend fun me() = newsfeedService.me()
}