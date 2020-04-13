package com.healthdiary.model.data.localstorage

import androidx.room.TypeConverter
import java.util.*


class TypeConverter {
    @TypeConverter
    fun longToDate(value : Long?) : Date? = value?.let{ Date(it) }

    @TypeConverter
    fun dateToLong(value : Date?) : Long? = value?.let { it.time }
}