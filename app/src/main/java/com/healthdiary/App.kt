package com.healthdiary

import android.app.Application
import com.healthdiary.di.appModule
import com.healthdiary.di.calendarModule
import com.healthdiary.di.homeModule
import com.healthdiary.di.profileModule
import com.healthdiary.model.data.localstorage.DataBase
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.localstorage.entities.note.EntityNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    var db : DataBase? = null

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(listOf(appModule, homeModule, calendarModule, profileModule))
        }

        db = DataBase.getDataBase(this.applicationContext)

        GlobalScope.launch(Dispatchers.IO) {
            Timber.d("Start Coroutine")
            val notes = LocalDataSource.getNotesByIndicatorId(1)
            for (note in notes) {
                val model =
                    EntityNote(
                        date = note.date,
                        indicatorId = note.indicator.id,
                        comment = note.comment
                    )
                db?.daoModel()?.saveNote(model)
            }
            Timber.d("Saving Done")
        }


    }
}