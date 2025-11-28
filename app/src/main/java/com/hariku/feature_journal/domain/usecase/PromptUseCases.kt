package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.PromptRequest
import com.hariku.feature_journal.domain.model.PromptResponse
import com.hariku.feature_journal.domain.repository.PromptRepository

class GeneratePromptsUseCase(
    private val repository: PromptRepository
) {
    suspend operator fun invoke(request: PromptRequest): Result<PromptResponse> {
        return repository.generatePrompts(request)
    }
}