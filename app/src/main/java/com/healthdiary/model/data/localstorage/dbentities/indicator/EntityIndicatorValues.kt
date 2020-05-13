package com.healthdiary.model.data.localstorage.dbentities.indicator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "indicator_values")
class EntityIndicatorValues (
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "parameter_id") val parameterId : Int,
    @ColumnInfo(name = "title")val title : String? = ""
    )