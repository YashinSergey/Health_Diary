package com.healthdiary.model.entities

import java.util.*

data class Indicator(
    val id: Int,
    val title: String,
    val unit: String,
    val icon: Int,
    var parameters: List<IndicatorParameter>? = null,
    val isActive: Boolean = true)