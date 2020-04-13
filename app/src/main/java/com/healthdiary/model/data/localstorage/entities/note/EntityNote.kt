package com.healthdiary.model.data.localstorage.entities.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.healthdiary.model.entities.Indicator
import java.util.*

@Entity(tableName = "notes")
data class EntityNote(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "indicator_id") val indicatorId: Int,
    val date: Date,
    val comment: String
)