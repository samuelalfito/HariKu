package com.hariku.feature_home.domain.usecase

import com.hariku.feature_home.domain.model.MoodModel
import com.hariku.feature_home.domain.repository.MoodRepository

class SaveMoodUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(mood: MoodModel): Result<Unit> {
        return repository.saveMood(mood)
    }
}

