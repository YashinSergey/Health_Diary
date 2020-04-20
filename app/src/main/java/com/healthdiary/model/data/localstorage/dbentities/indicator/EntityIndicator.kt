package com.healthdiary.model.data.localstorage.dbentities.indicator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "indicators")
data class EntityIndicator(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val unit: String,
    @ColumnInfo(name = "icon") val icon: Int,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)