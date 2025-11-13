package com.hariku.feature_home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: MoodEntity)

    @Query("SELECT * FROM moods WHERE userId = :userId AND date = :date LIMIT 1")
    suspend fun getMoodByDate(userId: String, date: String): MoodEntity?

    @Query("SELECT * FROM moods WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getAllMoods(userId: String): List<MoodEntity>

    @Query("DELETE FROM moods WHERE id = :id")
    suspend fun deleteMood(id: String)
}

