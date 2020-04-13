package com.healthdiary.model.data.localstorage.entities.indicator.parameter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "parameter_values")
class EntityIParameterValues (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "parameter_id") val parameterId : Int,
    val value : Int
)