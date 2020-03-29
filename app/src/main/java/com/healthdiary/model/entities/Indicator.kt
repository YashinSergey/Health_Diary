package com.healthdiary.model.entities

data class Indicator(
    val id: Int,
    val title: String,
    val values: String,
    val unit: String,
    val icon: Int)