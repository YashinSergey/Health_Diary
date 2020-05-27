package com.healthdiary.model.data.localstorage.dbentities.query

import androidx.room.ColumnInfo
import java.util.*

class EntityLastValueByIndicatorId (
    val date : Date,
    val value : Float,
    val indicator_value_id : Int
)