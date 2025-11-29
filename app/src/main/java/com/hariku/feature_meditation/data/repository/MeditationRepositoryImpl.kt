package com.hariku.feature_meditation.data.repository

import com.hariku.feature_meditation.data.local.MeditationSongDao
import com.hariku.feature_meditation.data.mapper.MeditationSongMapper
import com.hariku.feature_meditation.data.remote.MeditationRemoteDataSource
import com.hariku.feature_meditation.domain.model.MeditationSongModel
import com.hariku.feature_meditation.domain.repository.MeditationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MeditationRepositoryImpl(
    private val remoteDataSource: MeditationRemoteDataSource,
    private val dao: MeditationSongDao
) : MeditationRepository {
    
    override suspend fun getAllSongs(): Result<List<MeditationSongModel>> {
        return try {
            val remoteSongs = remoteDataSource.getAllSongs()
            val domainModels = MeditationSongMapper.toDomainList(remoteSongs)
            
            val entities = MeditationSongMapper.toEntityList(domainModels)
            dao.insertSongs(entities)
            
            Result.success(domainModels)
        } catch (e: Exception) {
            try {
                val localSongs = dao.getAllSongs()
                val domainModels = MeditationSongMapper.fromEntityList(localSongs)
                Result.success(domainModels)
            } catch (localError: Exception) {
                Result.failure(localError)
            }
        }
    }
    
    override suspend fun getSongById(songId: String): Result<MeditationSongModel> {
        return try {
            val entity = dao.getSongById(songId)
            if (entity != null) {
                val domainModel = MeditationSongMapper.fromEntity(entity)
                Result.success(domainModel)
            } else {
                Result.failure(Exception("Song not found with id: $songId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getSongsByCategory(category: String): Result<List<MeditationSongModel>> {
        return try {
            val entities = dao.getSongsByCategory(category)
            val domainModels = MeditationSongMapper.fromEntityList(entities)
            Result.success(domainModels)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun observeAllSongs(): Flow<List<MeditationSongModel>> {
        return dao.observeAllSongs().map { entities ->
            MeditationSongMapper.fromEntityList(entities)
        }
    }
}