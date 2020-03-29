package com.healthdiary

import android.app.Application
import com.healthdiary.di.appModule
import com.healthdiary.di.calendarModule
import com.healthdiary.di.homeModule
import com.healthdiary.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(listOf(appModule, homeModule, calendarModule, profileModule))
        }
    }
}