package com.hariku.feature_home.domain.model

data class MoodModel(
    val id: String = "",
    val userId: String = "",
    val moodType: String = "",
    val date: String = "", // Format: "yyyy-MM-dd"
    val timestamp: Long = 0L
)

