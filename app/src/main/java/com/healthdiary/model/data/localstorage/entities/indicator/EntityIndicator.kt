package com.healthdiary.model.data.localstorage.entities.indicator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "indicators")
data class EntityIndicator(
    @PrimaryKey val id: Int,
    val title: String,
    val unit: String,
    val iconRes: Int,
    @ColumnInfo(name = "is_active") val isActive: Boolean
)