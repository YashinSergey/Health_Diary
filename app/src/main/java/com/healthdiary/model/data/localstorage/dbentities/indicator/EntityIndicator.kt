package com.healthdiary.model.data.localstorage.dbentities.indicator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "indicators")
data class EntityIndicator(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")val id: Int? = null,
    @ColumnInfo(name= "title")val title: String,
    @ColumnInfo(name= "unit")val unit: String,
    @ColumnInfo(name = "icon") val icon: Int,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)