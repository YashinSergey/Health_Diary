package com.healthdiary.model.entities

import java.util.*

data class Note(
    val id: Int ? = Random().nextInt(1000000),
    val date: Date,
    val indicator: Indicator,
    val value : List<Float>? = listOf(),
    val comment: String)

