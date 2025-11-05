package com.hariku.feature_home.data.repository

import com.hariku.feature_home.data.local.MoodDao
import com.hariku.feature_home.data.mapper.MoodMapper
import com.hariku.feature_home.data.remote.MoodFirebaseService
import com.hariku.feature_home.domain.model.MoodModel
import com.hariku.feature_home.domain.repository.MoodRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoodRepositoryImpl(
    private val moodDao: MoodDao,
    private val firebaseService: MoodFirebaseService
) : MoodRepository {

    override suspend fun saveMood(mood: MoodModel): Result<Unit> {
        return try {
            // Save to local database first
            val entity = MoodMapper.toEntity(mood)
            moodDao.insertMood(entity)

            // Then sync to Firebase
            val dto = MoodMapper.fromDomain(mood)
            firebaseService.saveMood(mood.userId, dto)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMoodByDate(userId: String, date: String): MoodModel? {
        return try {
            // Try local first
            val localMood = moodDao.getMoodByDate(userId, date)
            if (localMood != null) {
                return MoodMapper.fromEntity(localMood)
            }

            // Fallback to Firebase
            val remoteMood = firebaseService.getMoodByDate(userId, date)
            remoteMood?.let { MoodMapper.toDomain(it) }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAllMoods(userId: String): List<MoodModel> {
        return try {
            // Try local first
            val localMoods = moodDao.getAllMoods(userId)
            if (localMoods.isNotEmpty()) {
                return localMoods.map { MoodMapper.fromEntity(it) }
            }

            // Fallback to Firebase
            val remoteMoods = firebaseService.getAllMoods(userId)
            remoteMoods.map { MoodMapper.toDomain(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getTodayMood(userId: String): MoodModel? {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return getMoodByDate(userId, today)
    }
}

