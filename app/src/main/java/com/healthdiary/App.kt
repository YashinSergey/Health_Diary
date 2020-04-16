package com.healthdiary

import android.app.Application
import com.healthdiary.di.appModule
import com.healthdiary.di.calendarModule
import com.healthdiary.di.homeModule
import com.healthdiary.di.profileModule
import com.healthdiary.model.data.localstorage.DataBase
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityParameterValues
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import timber.log.Timber
import kotlin.random.Random

class App : Application() {
    var db: DataBase? = null

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(listOf(appModule, homeModule, calendarModule, profileModule))
        }

        db = DataBase.getDataBase(this.applicationContext)

        LocalDataSource.initMockDBContent(db)
    }


}