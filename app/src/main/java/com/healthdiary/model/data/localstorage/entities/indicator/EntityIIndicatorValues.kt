package com.healthdiary.model.data.localstorage.entities.indicator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "indicator_values")
class EntityIIndicatorValues (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "indicator_id") val indicatorId : Int,
    val title : String? = ""
    )