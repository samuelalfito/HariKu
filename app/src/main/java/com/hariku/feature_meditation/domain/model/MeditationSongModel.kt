package com.hariku.feature_meditation.domain.model

data class MeditationSongModel(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val imageResName: String = "",
    val audioResName: String = "",
    val durationMs: Long = 0L,
    val description: String = ""
)