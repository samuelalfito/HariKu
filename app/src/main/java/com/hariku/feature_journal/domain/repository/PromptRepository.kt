package com.hariku.feature_journal.domain.repository

import com.hariku.feature_journal.domain.model.PromptRequest
import com.hariku.feature_journal.domain.model.PromptResponse
import kotlinx.coroutines.flow.Flow

interface PromptRepository {
    suspend fun generatePrompts(request: PromptRequest): Result<PromptResponse>
    
    fun observeCachedPrompts(): Flow<PromptResponse?>
    
    suspend fun clearCache()
}