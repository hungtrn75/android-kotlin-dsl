package com.skymapglobal.cctest.core.di

import android.content.Context
import com.skymapglobal.cctest.core.util.AuthInterceptor
import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.auth.data.remote.AuthRemoteDataSource
import com.skymapglobal.cctest.workspace.auth.data.remote.AuthRemoteDataSourceImpl
import com.skymapglobal.cctest.workspace.auth.data.remote.AuthService
import com.skymapglobal.cctest.workspace.auth.data.repository.AuthRepoImpl
import com.skymapglobal.cctest.workspace.auth.domain.repository.AuthRepo
import com.skymapglobal.cctest.workspace.auth.domain.usecase.LoginUseCase
import com.skymapglobal.cctest.workspace.auth.domain.usecase.MeUseCase
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    fun provideInterceptor(context: Context): AuthInterceptor {
        return AuthInterceptor(context)
    }

    fun provideHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.readTimeout(45, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(45, TimeUnit.SECONDS)
        return okHttpClientBuilder.addInterceptor(interceptor).build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(client)
            .build()
    }

    single { provideInterceptor(get()) }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get()) }
}

val serviceModule = module {
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
    single {
        provideAuthService(get())
    }
}

val dataSourceModule = module {
    single<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(get())
    }
}

val repositoryModule = module {
    single<AuthRepo> {
        AuthRepoImpl(get())
    }
}

val useCaseModule = module {
    single {
        LoginUseCase(get())
    }
    single {
        MeUseCase(get())
    }
}