package com.healthdiary.model.data.localstorage.dbentities.query

import androidx.room.ColumnInfo
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import java.util.*

class EntityNoteForDataPoint (
    val date: Date,
    @ColumnInfo(name ="indicator_value_id") val id: Int,
    val value: Float
)