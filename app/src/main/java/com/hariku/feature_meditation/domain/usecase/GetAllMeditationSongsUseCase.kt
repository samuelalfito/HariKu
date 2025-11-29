package com.hariku.feature_meditation.domain.usecase

import com.hariku.feature_meditation.domain.model.MeditationSongModel
import com.hariku.feature_meditation.domain.repository.MeditationRepository

class GetAllMeditationSongsUseCase(
    private val repository: MeditationRepository
) {
    suspend operator fun invoke(): Result<List<MeditationSongModel>> {
        return repository.getAllSongs()
    }
}