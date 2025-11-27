package com.hariku.feature_journal.data.repository

import android.util.Log
import com.hariku.feature_journal.data.local.dao.PromptDao
import com.hariku.feature_journal.data.mapper.PromptMapper
import com.hariku.feature_journal.data.remote.PromptAIService
import com.hariku.feature_journal.domain.model.PromptRequest
import com.hariku.feature_journal.domain.model.PromptResponse
import com.hariku.feature_journal.domain.repository.PromptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class PromptRepositoryImpl(
    private val aiService: PromptAIService,
    private val dao: PromptDao
) : PromptRepository {
    
    companion object {
        private const val TAG = "PromptRepository"
    }
    
    override suspend fun generatePrompts(request: PromptRequest): Result<PromptResponse> {
        return try {
            val response = aiService.generatePrompts(request)
            
            val cacheEntity = PromptMapper.toCacheEntity(response)
            dao.cachePrompt(cacheEntity)
            
            Result.success(response)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to generate prompts: ${e.message}", e)
            val cached = dao.getLatestPrompt()
            if (cached != null) {
                Result.success(PromptMapper.fromCacheEntity(cached))
            } else {
                Result.failure(e)
            }
        }
    }
    
    override fun observeCachedPrompts(): Flow<PromptResponse?> {
        return dao.observeLatestPrompt()
            .onEach { entity ->
                if (entity != null) {
                    Log.d(TAG, "Cache emitted: ${entity.introduction.take(50)}...")
                } else {
                    Log.w(TAG, "Cache emitted: null")
                }
            }
            .map { entity ->
                entity?.let { PromptMapper.fromCacheEntity(it) }
            }
    }
    
    override suspend fun clearCache() {
        dao.clearCache()
    }
}