package com.healthdiary.model.data.localstorage.dbentities.indicator.parameter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "indicator_parameters")
class EntityIndicatorParameters (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "indicator_id") val indicatorId : Int
)