package com.hariku.feature_home.domain.usecase

import com.hariku.feature_home.domain.model.MoodModel
import com.hariku.feature_home.domain.repository.MoodRepository

class GetTodayMoodUseCase(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(userId: String): MoodModel? {
        return repository.getTodayMood(userId)
    }
}

