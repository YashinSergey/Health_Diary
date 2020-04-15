package com.healthdiary.model.data.localstorage.entities.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class EntityNote(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "indicator_id") val indicatorId: Int,
    val date: Date,
    val comment: String
)