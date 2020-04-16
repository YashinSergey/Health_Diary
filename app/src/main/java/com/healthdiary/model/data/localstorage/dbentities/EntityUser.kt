package com.healthdiary.model.data.localstorage.dbentities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class EntityUser(

    @PrimaryKey val name: String,
    val birthday: Long,
    val gender: Int
)