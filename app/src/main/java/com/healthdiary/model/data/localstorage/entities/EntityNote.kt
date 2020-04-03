package com.healthdiary.model.data.localstorage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.healthdiary.model.entities.Indicator
import java.util.*

@Entity
data class EntityNote(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Date,
    val indicator: Indicator,
    val Value: Float,
    val Comment: String
)