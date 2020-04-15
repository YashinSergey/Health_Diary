package com.healthdiary.model.entities

import java.util.*

data class Indicator(
    val id: Int ? = Random().nextInt(1000000),
    val title: String,
    val unit: String,
    val icon: Int,
    val isActive: Boolean = true)