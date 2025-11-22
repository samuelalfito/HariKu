package com.hariku.feature_home.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moods")
data class MoodEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val moodType: String,
    val date: String,
    val timestamp: Long
)

