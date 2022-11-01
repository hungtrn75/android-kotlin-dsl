package com.skymapglobal.cctest.workspace.auth.data.remote

class AuthRemoteDataSourceImpl constructor(private val authService: AuthService) :
    AuthRemoteDataSource {
    override suspend fun login(body: Map<String, String>) = authService.login(body)

    override suspend fun me() = authService.me()
}