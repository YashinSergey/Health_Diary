package com.healthdiary.model.entities

import java.util.*

data class Indicator(
    val id: Int,
    val title: String,
    val values: List<Pair<Date, String>>,
    val unit: String,
    val icon: Int)