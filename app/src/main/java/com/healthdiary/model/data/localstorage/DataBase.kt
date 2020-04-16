package com.healthdiary.model.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.EntityUser
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIParameterValues
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValue

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