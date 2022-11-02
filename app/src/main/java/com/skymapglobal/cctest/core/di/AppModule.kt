package com.skymapglobal.cctest.core.di

import android.content.Context
import com.skymapglobal.cctest.core.util.AuthInterceptor
import com.skymapglobal.cctest.core.util.Constants
import com.skymapglobal.cctest.workspace.newsfeed.data.local.NewsfeedLocalDataSource
import com.skymapglobal.cctest.workspace.newsfeed.data.local.NewsfeedLocalDataSourceImpl
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedRemoteDataSource
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedRemoteDataSourceImpl
import com.skymapglobal.cctest.workspace.newsfeed.data.remote.NewsfeedService
import com.skymapglobal.cctest.workspace.newsfeed.data.repository.NewsfeedRepoImpl
import com.skymapglobal.cctest.workspace.newsfeed.domain.repository.NewsfeedRepo
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetNewsUseCase
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import com.skymapglobal.cctest.workspace.newsfeed.presentation.NewsfeedViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
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
            .addConverterFactory(JacksonConverterFactory.create())
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
    single<NewsfeedLocalDataSource> {
        NewsfeedLocalDataSourceImpl(get())
    }
}

val repositoryModule = module {
    single<NewsfeedRepo> {
        NewsfeedRepoImpl(get(), get())
    }
}

val useCaseModule = module {
    single {
        GetTopHeadingsUseCase(get())
    }
    single {
        GetNewsUseCase(get())
    }
}

val viewModelModule = module {
    viewModel {
        NewsfeedViewModel(get(), get())
    }
}