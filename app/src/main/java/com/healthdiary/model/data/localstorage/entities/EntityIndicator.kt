package com.healthdiary.model.data.localstorage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntityIndicator(
    @PrimaryKey val id: Int,
    val title: String,
    val unit: String,
    val iconRes: Int,
    val isActive: Boolean
)