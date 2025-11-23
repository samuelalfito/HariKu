package com.hariku.feature_meditation.data.dto

data class MeditationSongDto(
    val id: String? = null,
    val title: String? = null,
    val category: String? = null,
    val imageResName: String? = null,
    val audioResName: String? = null,
    val durationMs: Long? = null,
    val description: String? = null
)