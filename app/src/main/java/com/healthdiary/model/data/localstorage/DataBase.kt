package com.healthdiary.model.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.healthdiary.model.data.localstorage.entities.EntityIndicator
import com.healthdiary.model.data.localstorage.entities.EntityNote
import com.healthdiary.model.data.localstorage.entities.EntityUser
import io.reactivex.subjects.PublishSubject

@Database(entities = [EntityUser::class, EntityIndicator::class, EntityNote::class], version = 1)
abstract class DataBase() : RoomDatabase() {

    abstract fun daoModel() : DaoModel

    companion object {
        var INSTANCE: DataBase? = null

        fun destroyDataBase() {
            INSTANCE = null
        }

        fun getDataBase(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "HealthDiaryDB"
                    ).build()
                }
            }
            return INSTANCE
        }

    }





}