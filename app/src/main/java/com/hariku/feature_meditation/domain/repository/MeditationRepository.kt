package com.hariku.feature_meditation.domain.repository

import com.hariku.feature_meditation.domain.model.MeditationSongModel
import kotlinx.coroutines.flow.Flow

interface MeditationRepository {
    
    suspend fun getAllSongs(): Result<List<MeditationSongModel>>
    
    suspend fun getSongById(songId: String): Result<MeditationSongModel>
    
    suspend fun getSongsByCategory(category: String): Result<List<MeditationSongModel>>
    
    fun observeAllSongs(): Flow<List<MeditationSongModel>>
}