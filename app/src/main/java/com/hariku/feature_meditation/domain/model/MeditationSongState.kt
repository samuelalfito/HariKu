package com.hariku.feature_meditation.domain.model

data class MeditationSongState(
    val song: MeditationSongModel? = null,
    val isPlaying: Boolean = false,
    val isCompleted: Boolean = false,
    val duration: Float = 0f,
    val isLoading: Boolean = false,
    val error: String? = null
)