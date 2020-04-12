package com.healthdiary

import android.app.Application
import com.healthdiary.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, homeModule, calendarModule, profileModule, indicatorModule))
        }
    }
}