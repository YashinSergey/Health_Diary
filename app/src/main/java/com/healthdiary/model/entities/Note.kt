package com.healthdiary.model.entities

import java.util.*

data class Note(
    val id: Int? = null,
    val date: Date,
    val indicator: Indicator,
    val value: Float,
    val parameters: List<IndicatorParameter>? = null,
    val comment: String = "")