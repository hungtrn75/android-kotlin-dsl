package com.skymapglobal.cctest.core.di

import android.content.Context
import com.skymapglobal.cctest.core.util.AuthInterceptor
import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedRemoteDataSource
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedRemoteDataSourceImpl
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedService
import com.skymapglobal.cctest.workspace.newsfeed.data.repository.NewsfeedRepoImpl
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.LoginUseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.MeUseCase
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
    fun provideAuthService(retrofit: Retrofit): NewsfeedService {
        return retrofit.create(NewsfeedService::class.java)
    }
    single {
        provideAuthService(get())
    }
}

val dataSourceModule = module {
    single<NewsfeedRemoteDataSource> {
        NewsfeedRemoteDataSourceImpl(get())
    }
}

val repositoryModule = module {
    single<NewsfeedRepo> {
        NewsfeedRepoImpl(get())
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