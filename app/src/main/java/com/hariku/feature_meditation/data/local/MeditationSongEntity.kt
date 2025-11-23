package com.hariku.feature_meditation.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meditation_songs")
data class MeditationSongEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String,
    val imageResName: String,
    val audioResName: String,
    val durationMs: Long,
    val description: String
)