package com.healthdiary.model.entities

import java.time.LocalDate

data class Note(
    val id: Int,
    val date: LocalDate,
    val indicators: List<Indicator>,
    val comment: String)