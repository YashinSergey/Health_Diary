package com.healthdiary.model.data.localstorage.dbentities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class EntityUser(

    @PrimaryKey
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "birthday") val birthday: Long,
    @ColumnInfo(name = "gender") val gender: Int
)