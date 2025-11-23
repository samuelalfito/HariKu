package com.hariku.feature_meditation.domain.model

data class MeditationState(
    val songs: List<MeditationSongModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)