package com.healthdiary.model.data.localstorage

import androidx.room.TypeConverter
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.entities.Indicator
import java.util.*


class TypeConverter {
    @TypeConverter
    fun longToDate(value : Long?) : Date? = value?.let{ Date(it) }

    @TypeConverter
    fun dateToLong(value : Date?) : Long? = value?.let { it.time }

//    @TypeConverter
//    fun EntityIndicatorToIndicator(value : EntityIndicator?) : Indicator? = value?.let{ Indicator(id= it.id!!, title = it.title, unit = it.unit, icon = it.icon, parameters = null, isActive = it.isActive) }
}