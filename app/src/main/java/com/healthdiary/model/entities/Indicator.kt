package com.healthdiary.model.entities

data class Indicator(
    val id: Int,
    val title: String,
    val unit: String,
    val icon: Int,
    val isActive: Boolean = true)