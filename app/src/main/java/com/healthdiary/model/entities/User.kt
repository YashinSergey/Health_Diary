package com.healthdiary.model.entities

import java.util.*

const val MALE = 1
const val FEMALE = 0

data class User (
    val name : String,
    val birthday : Date,
    val Gender : Int
    )