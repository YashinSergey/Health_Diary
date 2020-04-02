package com.healthdiary.model.data.localstorage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

const val MALE = 1
const val FEMALE = 0

@Entity
data class EntityUser(

    @PrimaryKey val name: String,
    val birthday: Date,
    val gender: Int
)