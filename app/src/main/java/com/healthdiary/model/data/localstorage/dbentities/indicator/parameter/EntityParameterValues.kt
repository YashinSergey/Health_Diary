package com.healthdiary.model.data.localstorage.dbentities.indicator.parameter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "parameter_values")
class EntityParameterValues (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "parameter_id") val parameterId : Int,
    val value : String
)