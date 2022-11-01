package com.skymapglobal.cctest

import android.app.Application
import com.skymapglobal.cctest.core.di.*
import com.skymapglobal.cctest.core.util.DebugTree
import com.skymapglobal.cctest.core.util.NotLoggingTree
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    retrofitModule,
                    serviceModule,
                    dataSourceModule,
                    repositoryModule,
                    useCaseModule,
                )
            )
        }
        if (!BuildConfig.DEBUG) {
            Timber.plant(NotLoggingTree())
        } else {
            Timber.plant(DebugTree())
        }
    }
}
