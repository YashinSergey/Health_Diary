package com.healthdiary.model.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.healthdiary.model.data.localstorage.dbentities.EntityUser
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityParameterValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValues

@Database(
    entities = [
        EntityUser::class,
        EntityIndicator::class,
        EntityIndicatorValues::class,
        EntityIndicatorParameters::class,
        EntityParameterValues::class,
        EntityNote::class,
        EntityNoteParameters::class,
        EntityNoteValues::class
    ],
    version = 1
)
@TypeConverters(TypeConverter::class)
abstract class DataBase() : RoomDatabase() {

    abstract fun daoModel(): DaoModel

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