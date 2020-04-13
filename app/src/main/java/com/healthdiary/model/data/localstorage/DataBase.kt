package com.healthdiary.model.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.healthdiary.model.data.localstorage.entities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.entities.note.EntityNote
import com.healthdiary.model.data.localstorage.entities.EntityUser
import com.healthdiary.model.data.localstorage.entities.indicator.EntityIIndicatorValues
import com.healthdiary.model.data.localstorage.entities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.entities.indicator.parameter.EntityIParameterValues
import com.healthdiary.model.data.localstorage.entities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.entities.note.EntityNoteValue

@Database(entities = [EntityUser::class, EntityIndicator::class, EntityIIndicatorValues::class,  EntityIndicatorParameters::class, EntityIParameterValues::class,  EntityNote::class, EntityNoteParameters::class, EntityNoteValue::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class DataBase() : RoomDatabase() {

    abstract fun daoModel() : DaoModel

    companion object {
        var INSTANCE: DataBase? = null

        fun destroyDataBase() {
            INSTANCE = null
        }

        fun getDataBase(context: Context? = null): DataBase? {
            context?.let {
                if (INSTANCE == null) {
                    synchronized(DataBase::class) {
                        INSTANCE = Room.databaseBuilder(
                            it.applicationContext,
                            DataBase::class.java,
                            "HealthDiaryDB"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }

    }





}