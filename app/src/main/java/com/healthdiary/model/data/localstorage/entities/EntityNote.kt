package com.healthdiary.model.data.localstorage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class EntityNote(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Date,
    val indicatorId: Int,
    val Value: Float,
    val Comment: String
)