package com.hariku.feature_meditation.domain.usecase

import com.hariku.feature_meditation.domain.model.MeditationSongModel
import com.hariku.feature_meditation.domain.repository.MeditationRepository

class GetMeditationSongByIdUseCase(
    private val repository: MeditationRepository
) {
    suspend operator fun invoke(songId: String): Result<MeditationSongModel> {
        return repository.getSongById(songId)
    }
}