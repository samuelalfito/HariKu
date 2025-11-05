package com.hariku.feature_home.domain.repository

import com.hariku.feature_home.domain.model.MoodModel

interface MoodRepository {
    suspend fun saveMood(mood: MoodModel): Result<Unit>
    suspend fun getMoodByDate(userId: String, date: String): MoodModel?
    suspend fun getAllMoods(userId: String): List<MoodModel>
    suspend fun getTodayMood(userId: String): MoodModel?
}

