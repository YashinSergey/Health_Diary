package com.healthdiary.model.entities

import java.util.*

data class Note(
    val id: Int,
    val date: Date,
    val indicators: List<Indicator>,
    val comment: String)