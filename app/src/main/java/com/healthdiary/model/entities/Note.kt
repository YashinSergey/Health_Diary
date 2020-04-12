package com.healthdiary.model.entities

import java.util.*

data class Note(
    val id: Int,
    val date: Date,
    val indicator: Indicator,
    val value: Float,
    val parameters: List<Pair<IndicatorParameter, String>>? = null,
    val comment: String = "")